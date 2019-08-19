package com.snapdeal.cps.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class PogCsvWriter {

    private File file;
    private char separator;
    private String[] fieldMap;
    
    public PogCsvWriter(File file, char separator, String fieldMap){
        this.file = file;
        this.separator = separator;
        this.fieldMap = fieldMap.split(",");
    }
    
    public File getFile() {
        return file;
    }

    public char getSeparator() {
        return separator;
    }

    public String[] getFieldMap() {
        return fieldMap;
    }
    
    public String[] getHeaderValues() {
        String[] headerColumns = new String[fieldMap.length];
        
        for(int i=0;i<fieldMap.length;i++){
            headerColumns[i] = fieldMap[i].split(":")[0];
        }
        return headerColumns;
    }
    
    private String[] convertToStringArray(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        String[] objectArray = new String[fieldMap.length];
        
        for(int i=0;i<fieldMap.length;i++){
            objectArray[i] = BeanUtils.getProperty(object, fieldMap[i].split(":")[1]);
        }
        
        return objectArray;
    }
    
/*    private String[] convertSPToStringArray(SokratiProductDTO pogSRO) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        String[] pogArray = new String[fieldMap.length];
        
        for(int i=0;i<fieldMap.length;i++){
            pogArray[i] = BeanUtils.getProperty(pogSRO, fieldMap[i].split(":")[1]);
        }
        
        return pogArray;
    }*/
    
    public void writeToFile(List<Object> objectList) throws Exception{
        CSVWriter csvWriter = null;
        try{
            csvWriter = new CSVWriter(new FileWriter(file), separator, CSVWriter.NO_QUOTE_CHARACTER);
            
            // Write header
            csvWriter.writeNext(getHeaderValues());
            
            // Write Data
            for(Object pogDTO : objectList){
                csvWriter.writeNext(convertToStringArray(pogDTO));
            }
            
            csvWriter.flush();
            
        }catch (IOException ioe) {
            throw ioe;
        }catch(Exception ex){
            throw ex;
        }finally{
            if(csvWriter != null){
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        
        
        
    }
    
/*    public void writeSPToFile(List<SokratiProductDTO> pogDTOList)throws Exception {
        CSVWriter csvWriter = null;
        try{
            csvWriter = new CSVWriter(new FileWriter(file), separator, CSVWriter.NO_QUOTE_CHARACTER);
            
            // Write header
            csvWriter.writeNext(getHeaderValues());
            
            // Write Data
            for(SokratiProductDTO pogDTO : pogDTOList){
                csvWriter.writeNext(convertSPToStringArray(pogDTO));
            }
            
            csvWriter.flush();
            
        }catch (IOException ioe) {
            throw ioe;
        }catch(Exception ex){
            throw ex;
        }finally{
            if(csvWriter != null){
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }*/
}
