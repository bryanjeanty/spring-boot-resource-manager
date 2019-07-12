package com.resource.manager.resource.repository;
import java.util.List;
import com.resource.manager.resource.entity.Record;
 
public interface FormulaCustomMethods {
    public List<Record> findAllFormulas();
   
    public Record findFormulaById(int formulaId);
   
   public Record  updateFormulaById(int formulaId, Record record);
   
   public Record deleteFormulaById( int formulaId);
}      