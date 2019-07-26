package com.ais.brm.common.ruleobject;

import org.apache.commons.lang.StringUtils;

import java.util.stream.Stream;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public abstract  class RuleObject implements  IRuleObject {

    protected static  final String AND=" && ";
    protected static  final String OR=" || ";
    protected static  final String MQ=" >= ";
    protected static  final String MORE=" > ";
    protected static  final String EQ=" == ";
    protected static  final String NOT_EQ=" != ";

    protected static  final String POINT="'";


    protected static  final String LQ=" <= ";
    protected static  final String LESS=" < ";

    protected  static final String LEFT=" ( ";
    protected  static final String RIGHT=" ) ";

    protected  static final String SPACE="\\s+";

    protected  static final String EMPTY="empty";


    protected  static final String DIVIDED="/";



    protected boolean checkValueExits(String...str){
        boolean match = Stream.of(str).anyMatch(StringUtils::isBlank);
        return match;

    }




}
