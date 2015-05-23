package com.cout970.magneticraft.api.computer.impl;

import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.computer.IModuleCPU;
import com.cout970.magneticraft.api.computer.IModuleMemoryController;
import com.cout970.magneticraft.util.Log;

public class ModuleCPU_MIPS implements IModuleCPU{

	public IModuleMemoryController memory;
	public int[] registes = new int[32];
	public int HI = 0;
	public int LO = 0;
	public int regPC = 0;
	//exceptions
	public int regStatus = 0;
	public int regCause = 0;
	public int regEPC = 0;
	
	public boolean waiting = false;
	public int cpuCicles = -1;
	
	public ModuleCPU_MIPS(){}
	
	public void connectMemory(IModuleMemoryController ram){
		memory = ram;
	}

	@Override
	public boolean isRunning() {
		return cpuCicles >= 0;
	}

	@Override
	public void start() {
		cpuCicles = 0;
		memory.clear();
		regPC = 0x00400000;
		regStatus = 0x0000FFFF;
		for(int i = 0;i< 28;i++){
			setRegister(i, 0);
		}
		setRegister(28, 0x00000000);//gp
		setRegister(29, 0x7fffeffc);//sp
		setRegister(30, 0x00000000);//fp
		setRegister(31, 0x00000000);//ra
	}

	@Override
	public void stop() {
		cpuCicles = -1;
	}

	@Override
	public void iterate() {
		if(cpuCicles >= 0){
			cpuCicles += 5000;//Hz = speed * 20
			
			if (cpuCicles > 100000){
				cpuCicles = 100000;
			}
			
			while (cpuCicles > 0 && !waiting){
				--cpuCicles;
				executeInsntruction();
			}
			waiting = false;
		}
	}

	public void advancePC() {
		regPC = (regPC + 4);
	}
	
	public void setRegister(int s, int val) {
		if(s==0)return;
		registes[s] = val;
	}

	public int getRegister(int t) {
		return registes[t];
	}

	private void executeInsntruction() {
		int instruct = memory.readWord(regPC);
		advancePC();
		switch(CONTROL(instruct)){
		case R:
			TipeR(instruct);
			break;
		case J:
			TipeJ(instruct);
			break;
		case I:
			TipeI(instruct);
			break;
		case Exeption:
			Exception(instruct);
		default: break;
		}
	}
	
	public enum IntructionType{ R,I,J,Exeption,NOP }

	public IntructionType CONTROL(int instruct) {
		if(instruct == 0)
			return IntructionType.NOP;				//no action
		int opcode = ComputerUtils.getBitsFromInt(instruct, 26, 31, false);		
		if(instruct == 0x0000000c || opcode == 0x10)
			return IntructionType.Exeption;			//exception
		if(opcode == 0)
			return IntructionType.R;				//type R
		if(opcode == 0x2 || opcode == 0x3)
			return IntructionType.J;				//type J
		return IntructionType.I;					//type I
	}

	public void TipeR(int instruct) {
		int rs,rt,rd,shamt,func;
		long m1,m2,mt;
		
		func = ComputerUtils.getBitsFromInt(instruct, 0, 5, false);
		shamt = ComputerUtils.getBitsFromInt(instruct, 6, 10, false);
		rd = ComputerUtils.getBitsFromInt(instruct, 11, 15, false);
		rt = ComputerUtils.getBitsFromInt(instruct, 16, 20, false);
		rs = ComputerUtils.getBitsFromInt(instruct, 21, 25, false);

		switch(func){
		
		case 0x0://sll
			setRegister(rd, rt << shamt);
			break;
		case 0x2://srl
			setRegister(rd, rt >>> shamt);
			break;
		case 0x3://sra
			setRegister(rd, rt >> shamt);
			break;
		case 0x4://sllv
			setRegister(rd, rt << rs);
			break;
		case 0x6://srlv
			setRegister(rd, rt >> rs);
			break;
		case 0x7://srav
			setRegister(rd, rs >>> rt);
			break;
			
		case 0x8://jr
			if(getRegister(rs) == -1)return;
			regPC = getRegister(rs);
			break;
		case 0x9://jalr
			if(getRegister(rs) == -1)return;
			setRegister(31, regPC);
			regPC = getRegister(rs);
			break;
			
		case 0x10://mfhi
			setRegister(rd, HI);
			break;
		case 0x11://mthi
			HI = getRegister(rd);
			break;
		case 0x12://mflo
			setRegister(rd, LO);
			break;
		case 0x13://mflo
			LO = getRegister(rd);
			break;
			
		case 0x18://mult
			m1 = getRegister(rs);
			m2 = getRegister(rt);
			mt = m1 * m2;
			LO = (int)mt;
			HI = (int)(mt >> 32);
			break;
		case 0x19://multu
			m1 = getRegister(rs);
			m2 = getRegister(rt);
			m1 = (m1 << 32) >>> 32;
			m2 = (m2 << 32) >>> 32;
			mt = m1 * m2;
			LO = (int)mt;
			HI = (int)(mt >> 32);
			break;
		case 0x1a://div
			if(getRegister(rt) != 0){
				LO = getRegister(rs) / getRegister(rt);
				HI = getRegister(rs) % getRegister(rt);
			}else{
				throwException(2);
			}
			break;
		case 0x1b://divu
			m1 = getRegister(rs);
			m2 = getRegister(rt);
			m1 = (m1 << 32) >>> 32;
			m2 = (m2 << 32) >>> 32;
			if(m2 == 0){
				throwException(2);
			}else{
				LO = (int) (m1 / m2);
				HI = (int) (m1 % m2);
			}
			break;	
		case 0x20://add
			setRegister(rd, getRegister(rt) + getRegister(rs));
			break;
		case 0x21://addu
			m1 = getRegister(rs);
			m2 = getRegister(rt);
			m1 = (m1 << 32) >>> 32;
			m2 = (m2 << 32) >>> 32;
			mt = m1 + m2;
			setRegister(rd , (int) mt);
			break;
		case 0x22://sub
			setRegister(rd , getRegister(rs) - getRegister(rt));
			break;
		case 0x23://subu
			m1 = getRegister(rs);
			m2 = getRegister(rt);
			m1 = (m1 << 32) >>> 32;
			m2 = (m2 << 32) >>> 32;
			mt = m1 - m2;
			setRegister(rd , (int) mt);
			break;
		case 0x24://and
			setRegister(rd , getRegister(rt) & getRegister(rs));
			break;
		case 0x25://or
			setRegister(rd , getRegister(rt) | getRegister(rs));
			break;
		case 0x26://xor
			setRegister(rd , getRegister(rt) ^ getRegister(rs));
			break;
		case 0x27://nor
			setRegister(rd , ~(getRegister(rt) | getRegister(rs)));
			break;
			
		case 0x2a://slt
			if(getRegister(rs) < getRegister(rt))
				setRegister(rd, 1);
			else
				setRegister(rd, 0);
			break;
		case 0x2b://sltu
			m1 = getRegister(rs);
			m2 = getRegister(rt);
			m1 = (m1 << 32) >>> 32;
			m2 = (m2 << 32) >>> 32;
			if(m1 < m2)
				setRegister(rd, 1);
			else
				setRegister(rd, 0);
			break;
		default :
			throwException(1);
			break;
		}
	}

	public void TipeJ(int instruct) {
		int dir = ComputerUtils.getBitsFromInt(instruct, 0, 25, false);
		int code = ComputerUtils.getBitsFromInt(instruct, 26, 31, false);
		switch(code){
		case 0x2://j
			regPC &= 0xF0000000;
			regPC |= dir << 2;
			break;
		case 0x3://jal
			setRegister(31, regPC);
			regPC &= 0xF0000000;
			regPC |= dir << 2;
			break;
		default :
			throwException(1);
			break;
		}
	}
	
	private void TipeI(int instruct) {
		int opcode, rs, rt, inmed,inmedU;
		long m1 = 0,m2 = 0;
		
		opcode = ComputerUtils.getBitsFromInt(instruct, 26, 31, false);
		rs = ComputerUtils.getBitsFromInt(instruct, 21, 25, false);
		rt = ComputerUtils.getBitsFromInt(instruct, 16, 20, false);
		inmed = ComputerUtils.getBitsFromInt(instruct, 0, 15, true);
		inmedU = ComputerUtils.getBitsFromInt(instruct, 0, 15, false);
		
		switch(opcode){
		case 0x1:
			if(rt == 1){//bgez
				if(getRegister(rs) >= 0){
					regPC += (inmed << 2);
				}
			}else if(rt == 0){//bltz
				if(getRegister(rs) < 0){
					regPC += (inmed << 2);
				}
			}
			break;
		case 0x4://beq
			if(getRegister(rt) == getRegister(rs)){
				regPC += (inmed << 2);
			}
			break;
		case 0x5://bne
			if(getRegister(rt) != getRegister(rs)){
				regPC += (inmed << 2);
			}
			break;
		case 0x6://blez
			if(getRegister(rs) <= 0){
				regPC += (inmed << 2);
			}
			break;
		case 0x7://bgtz
			if(getRegister(rs) > 0){
				regPC += (inmed << 2);
			}
			break;
		case 0x8://addi
			setRegister(rt, getRegister(rs) + inmed);
			break;
		case 0x9://addiu
			setRegister(rt, getRegister(rs) + inmedU);
			break;
		case 0xa://slti
			if(getRegister(rs) < inmed)
				setRegister(rt, 1);
			else
				setRegister(rt, 0);
			break;
		case 0xb://sltiu
			m1 = getRegister(rs);
			m2 = inmed;
			m1 = (m1 << 32) >>> 32;
			m2 = (m2 << 32) >>> 32;
			if(m1 < m2)
				setRegister(rt, 1);
			else
				setRegister(rt, 0);
			break;
		case 0xc://andi
			setRegister(rt , getRegister(rs) & inmedU);
			break;
		case 0xd://ori
			setRegister(rt , getRegister(rs) | inmedU);
			break;
		case 0xe://xori
			setRegister(rt , getRegister(rs) ^ inmedU);
			break;
		case 0xf://lui
			setRegister(rt, inmedU << 16);
			break;
		case 0x18://llo
			setRegister(rt, (getRegister(rt) & 0xFFFF0000) | inmedU);
			break;
		case 0x19://lhi
			setRegister(rt, (getRegister(rt) & 0x0000FFFF) | (inmedU << 16));
			break;
			
		case 0x1a://trap
			break;
			
		case 0x20://lb
			setRegister(rt, memory.readByte(getRegister(rs)+inmed));
			break;
		case 0x21://lh
			setRegister(rt, (short)(memory.readWord(getRegister(rs)+inmed)));
			break;
		case 0x23://lw
			setRegister(rt, memory.readWord(getRegister(rs)+inmed));
			break;
		case 0x24://lbu
			setRegister(rt, memory.readByte(getRegister(rs)+inmed) & 0xFF);
			break;
		case 0x25://lhu
			setRegister(rt, memory.readWord(getRegister(rs)+inmed) & 0xFFFF);
			break;
		case 0x28://sb
			memory.writeByte(getRegister(rs)+inmed, (byte)(getRegister(rt) & 0xFF));
			break;
		case 0x29://sh
			memory.writeByte( getRegister(rs)+inmed, (byte)(getRegister(rt) & 0xFF));
			memory.writeByte( getRegister(rs)+inmed+1, (byte)(getRegister(rt) & 0xFF00));
			break;
		case 0x2b://sw
			memory.writeWord(getRegister(rs)+inmed, getRegister(rt));
			break;
		default :
			throwException(1);
			break;
		}
	}
	
	
	public void Exception(int instruct) {
		if(instruct == 0x42000010){//rfe return from exception
			regPC = regEPC;
			regStatus >>= 4;
			return;
		}else if(instruct == 0x0000000C){
			throwException(3);
			return;
		}
		int code = ComputerUtils.getBitsFromInt(instruct, 21, 25, false);
		int rt = ComputerUtils.getBitsFromInt(instruct, 16, 20, false);
		int rd = ComputerUtils.getBitsFromInt(instruct, 11, 15, false);
		if(code == 0x0){//mfc0 rd, rt
			int val = 0;
			if(rt == 12)val = regStatus;
			if(rt == 13)val = regCause;
			if(rt == 14)val = regEPC;
			setRegister(rd, val);
			return;
		}else if(code == 0x4){//mtc0 rd, rt
			int val = getRegister(rt);
			if(rt == 12)regStatus = val;
			if(rt == 13)regCause = val;
			if(rt == 14)regEPC = val;
			return;
		}
		throwException(1);
	}
	
	 // 3: syscall, 2: aritmatic, 1: invalid instuction, 0: external 
	public void throwException(int flag){
		if((regStatus & (flag+1)) == 0){
			return;
		}
		regCause = flag;
		regEPC = regPC;
		regStatus <<= 4;
		regPC = 0x4;
	}

	@Override
	public void loadRegisters(NBTTagCompound nbt) {
		registes = nbt.getIntArray("Regs");
		if(registes.length != 32)registes = new int[32];
		cpuCicles = nbt.getInteger("Cicles");
		regPC = nbt.getInteger("PC");
		HI = nbt.getInteger("HI");
		LO = nbt.getInteger("LO");
		regStatus = nbt.getInteger("Status");
		regCause = nbt.getInteger("Cause");
		regEPC = nbt.getInteger("EPC");
	}

	@Override
	public void saveRegisters(NBTTagCompound nbt) {
		nbt.setIntArray("Regs", registes);
		nbt.setInteger("Cicles", cpuCicles);
		nbt.setInteger("PC", regPC);
		nbt.setInteger("HI", HI);
		nbt.setInteger("LO", LO);
		nbt.setInteger("Status", regStatus);
		nbt.setInteger("Cause", regCause);
		nbt.setInteger("EPC", regEPC);
	}

	@Override
	public int getRegPC() {
		return regPC;
	}

	@Override
	public void setRegPC(int value) {
		regPC = value;
	}
}
