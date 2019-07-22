package com.resource.manager.resource.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.service.FormulaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/formulas")
 public class FormulaController{
 private final FormulaService formulaService;

    @Autowired
     public FormulaController(FormulaService formulaService){
      this.formulaService = formulaService;
  }

     @PostMapping
     @SuppressWarnings({"rawtypes"})
     public @ResponseBody Map createFormulaRecord(@Valid @RequestBody Record record){
         return formulaService.saveFormulaRecord(record);
     }
     @GetMapping
     @SuppressWarnings({"rawtypes"})
     public  List getAllFormulas(){
     return formulaService.findAllFormulas(); 
     }
     @GetMapping("/{id}")
     @SuppressWarnings({"rawtypes"})
     public @ResponseBody Map  getFormulaById(@PathVariable("id")int formulaId){
         return formulaService.findFormulaById(formulaId);
         
     }
     @PutMapping("/{id}")
     @SuppressWarnings({"rawtypes"})
     public @ResponseBody Map updateFormulaById(@PathVariable("id") int formulaId,@Valid @RequestBody Record record){
         return formulaService.updateFormulaById(formulaId, record);
     }
     @DeleteMapping("/{id}")
     @SuppressWarnings({"rawtypes"})
     public @ResponseBody Map deleteFormulaById(@PathVariable("id")int formulaId){
         return formulaService.deleteFormulaById(formulaId);
     }

  


 }
 
