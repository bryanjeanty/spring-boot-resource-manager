package com.resource.manager.resource.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.resource.manager.resource.entity.Formula;
import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.repository.FormulaRepository;
import com.resource.manager.resource.repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private FormulaRepository formulaRepository;

    public FormulaService(RecordRepository recordRepository, FormulaRepository formulaRepository){

        this.recordRepository = recordRepository;
        this.formulaRepository= formulaRepository;
    }

    @SuppressWarnings({"rawtypes"})
    public Map saveFormulaRecord(Record record){
        Formula formula= new Formula();
        Formula newFormula = formulaRepository.save(formula);

        record.setTypeId(newFormula.getId());
        Record newRecord= recordRepository.save(record);

        return convertFormulaToMap(newRecord);
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public List findAllFormulas(){
        List<Record> formulas= recordRepository.findAllResources();
        List myArr = new ArrayList();
        for(Record formula: formulas){
            myArr.add(convertFormulaToMap(formula));
        }

        return myArr;
    }
@SuppressWarnings({"rawtypes"})
public Map findFormulaById( int formulaId){
Record formula = recordRepository.findFormulaById(formulaId);
return  convertFormulaToMap(formula);
}
@SuppressWarnings({"rawtypes"})
public Map updateFormulaById( int formulaId, Record record){
Record updatedFormula= recordRepository.updateFormulaById(formulaId, record);
return convertFormulaToMap(updatedFormula);


}
@SuppressWarnings({"rawtypes"})
public Map deleteFormulaById(int formulaId){
    Record deletedFormula=  recordRepository.deleteFormulaById(formulaId);
    return convertFormulaToMap(deletedFormula);
}




 @SuppressWarnings({"rawtypes", "unchecked"})
    private Map convertFormulaToMap(Record formula) {
       Map formulaMap= new LinkedHashMap();
       List<String> keysList = new ArrayList<String>(Arrays.asList(formula.getKeys().split(", ")));
       List<String> valuesList = new ArrayList<String>(Arrays.asList(formula.getKeyValues().split(", ")));
       List<String> dataTypesList = new ArrayList<String>(Arrays.asList(formula.getDataTypes().split(", ")));
       
       formulaMap.put("id", formula.getTypeId());
       formulaMap.put("type", formula.getType());

        for (int i = 0; i < valuesList.size(); i++) {
            Map<String, String> myValuesMap = new LinkedHashMap<String, String>();

            myValuesMap.put("value", valuesList.get(i));
            myValuesMap.put("dataType", dataTypesList.get(i));

            formulaMap.put(keysList.get(i), myValuesMap);
        }
        return formulaMap;
    }
} 