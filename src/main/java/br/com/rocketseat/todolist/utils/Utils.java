package br.com.rocketseat.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    // depois de pegar tudo que for null, vamos fazer um cop
    public static void copyNonNullProperties(Object source, Object target){
        //
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    // array que pega todos os nomes das propiedades que estao null
    public static  String[] getNullPropertyNames(Object source){
        // interface para acessar as propiedades
        final BeanWrapper src = new BeanWrapperImpl(source);

        // obter as propiedades, gerando uma array com todas as propiedades
        PropertyDescriptor[] pds= src.getPropertyDescriptors();

        // conjunto com propriedades de valores null
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd: pds){
            // para cada propertyValue vou pegar o getName e o valor atual
            Object srcValue = src.getPropertyValue(pd.getName());
            // verfificamdp se é null, se for null eu adiciono a minha propiedade
            if (srcValue ==  null)
            {
                emptyNames.add(pd.getName());
            }
        }
        // criando um array com o tamanho do emptyNames
        String[] result = new String[emptyNames.size()];
        // converter o result para array
        return emptyNames.toArray(result);
    }
}
