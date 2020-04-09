package com.pantrychef.pantrychef.repositories;

import com.pantrychef.pantrychef.models.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InstructionsRepo extends JpaRepository<Instruction, Long> {
    public Instruction findInstructionById(Long id);
    public Instruction findInstructionByname(String instructionName);
}