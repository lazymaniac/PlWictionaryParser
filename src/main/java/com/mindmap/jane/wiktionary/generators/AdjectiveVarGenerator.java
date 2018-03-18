package com.mindmap.jane.wiktionary.generators;

import com.mindmap.jane.domain.AdjectiveDegreeVar;
import com.mindmap.jane.domain.AdjectiveVar;
import com.mindmap.jane.domain.CasesVar;
import com.mindmap.jane.domain.enumeration.AdjectiveDegreeQualifier;
import com.mindmap.jane.domain.enumeration.CasesFormQualifier;
import com.mindmap.jane.wiktionary.generators.form.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
// TODO add support for equivalent and ims
public class AdjectiveVarGenerator {

    private final static String Y_TIP = "y";

    private final static String I_TIP = "i";

    private final static String EMPTY_TIP = "";

    public AdjectiveVarGenerator() {
    }

    /**
     * Takes as argument title of adjective ending with -y or -i. In case when higher degree or highest aren't
     * empty also variables them.
     *
     * @param zeroDegree    topic of unit
     * @param higherDegree  higher degree.
     * @param highestDegree highest degree.
     * @param equivalent
     *@param ims @return AdjectiveVar
     */
    public AdjectiveVar generateAdjectiveVar(String zeroDegree, String higherDegree, String highestDegree, String equivalent, String ims) {
        AdjectiveVar result = new AdjectiveVar();

        generateZeroDegree(zeroDegree, result);

        generateHigherDegree(higherDegree, result);

        generateHighestDegree(higherDegree, highestDegree, result);

        return result;
    }

    private void generateHighestDegree(String higherDegree, String highestDegree, AdjectiveVar result) {
        if (StringUtils.isNotBlank(highestDegree)) {
            if (StringUtils.endsWith(highestDegree, Y_TIP)) {
                result.addAdjectiveDegree(genDegree(highestDegree, Y_TIP, AdjectiveDegreeQualifier.HIGHEST));
            } else if (StringUtils.endsWith(higherDegree, I_TIP)) {
                result.addAdjectiveDegree(genDegree(highestDegree, I_TIP, AdjectiveDegreeQualifier.HIGHEST));
            } else {
                result.addAdjectiveDegree(genDegree(highestDegree, EMPTY_TIP, AdjectiveDegreeQualifier.HIGHEST));
            }
        }
    }

    private void generateHigherDegree(String higherDegree, AdjectiveVar result) {
        if (StringUtils.isNotBlank(higherDegree)) {
            if (StringUtils.endsWith(higherDegree, Y_TIP)) {
                result.addAdjectiveDegree(genDegree(higherDegree, Y_TIP, AdjectiveDegreeQualifier.HIGHER));
            } else if (StringUtils.endsWith(higherDegree, I_TIP)) {
                result.addAdjectiveDegree(genDegree(higherDegree, I_TIP, AdjectiveDegreeQualifier.HIGHER));
            } else {
                result.addAdjectiveDegree(genDegree(higherDegree, EMPTY_TIP, AdjectiveDegreeQualifier.HIGHER));
            }
        }
    }

    private void generateZeroDegree(String zeroDegree, AdjectiveVar result) {
        if (StringUtils.isNotBlank(zeroDegree)) {
            if (StringUtils.endsWith(zeroDegree, Y_TIP)) {
                result.addAdjectiveDegree(genDegree(zeroDegree, Y_TIP, AdjectiveDegreeQualifier.ZERO));
            } else {
                result.addAdjectiveDegree(genDegree(zeroDegree, I_TIP, AdjectiveDegreeQualifier.ZERO));
            }
        }
    }

    private AdjectiveDegreeVar genDegree(String topic, String postfix, AdjectiveDegreeQualifier degree) {
        AdjectiveDegreeVar result = new AdjectiveDegreeVar(degree);

        result.addCasesVar(genLpMsOs(topic, postfix));
        result.addCasesVar(genLpZenOs(topic, postfix));
        result.addCasesVar(genLpNMsOs(topic, postfix));
        result.addCasesVar(genLmMsOs(topic, postfix));
        result.addCasesVar(genLmNMsOs(topic, postfix));

        return result;
    }

    private CasesVar genLmNMsOs(String topic, String postfix) {
        CasesVar result = new CasesVar(CasesFormQualifier.ADJ_SING_N);

        String body;

        if (postfix.equals(Y_TIP)) {
            body = topic.substring(0, topic.lastIndexOf(Y_TIP));
            result.setMianownik(body + postfix);
            result.setWolacz(body + postfix);
        } else {
            body = topic;
            result.setMianownik(body + PrzymOdmLmNmOsForm.mianownik);
            result.setWolacz(body + PrzymOdmLmNmOsForm.wolacz);
        }

        result.setDopelniacz(body + PrzymOdmLmNmOsForm.dopelniacz);
        result.setCelownik(body + PrzymOdmLmNmOsForm.celownik);
        result.setBiernik(body + PrzymOdmLmNmOsForm.biernik);
        result.setNarzednik(body + PrzymOdmLmNmOsForm.narzednik);
        result.setMiejscownik(body + PrzymOdmLmNmOsForm.miejscownik);

        return result;
    }

    private CasesVar genLmMsOs(String topic, String postfix) {
        CasesVar result = new CasesVar(CasesFormQualifier.ADJ_PLUR_M);

        String body;

        if (postfix.equals(Y_TIP)) {
            body = topic.substring(0, topic.lastIndexOf(Y_TIP));
            result.setMianownik(body + postfix);
            result.setWolacz(body + postfix);
            result.setMiejscownik(body + Y_TIP + PrzymOdmLmMsOsForm.miejscownik);
            result.setNarzednik(body + Y_TIP + PrzymOdmLmMsOsForm.narzednik);
            result.setBiernik(body + Y_TIP + PrzymOdmLmMsOsForm.biernik);
            result.setDopelniacz(body + Y_TIP + PrzymOdmLmMsOsForm.dopelniacz);
            result.setCelownik(body + Y_TIP + PrzymOdmLmMsOsForm.celownik);
        } else {
            body = topic;
            result.setMianownik(body);
            result.setWolacz(body);
            result.setMiejscownik(body + PrzymOdmLmMsOsForm.miejscownik);
            result.setNarzednik(body + PrzymOdmLmMsOsForm.narzednik);
            result.setBiernik(body + PrzymOdmLmMsOsForm.biernik);
            result.setDopelniacz(body + PrzymOdmLmMsOsForm.dopelniacz);
            result.setCelownik(body + PrzymOdmLmMsOsForm.celownik);

        }
        return result;
    }

    private CasesVar genLpNMsOs(String topic, String postfix) {
        CasesVar result = new CasesVar(CasesFormQualifier.ADJ_SING_N);

        String body;

        if (postfix.equals(Y_TIP)) {
            body = topic.substring(0, topic.lastIndexOf(Y_TIP));
            result.setMiejscownik(body + Y_TIP + PrzymOdmLpNieMsForm.miejscownik);
            result.setNarzednik(body + Y_TIP + PrzymOdmLpNieMsForm.narzednik);
            result.setCelownik(body + PrzymOdmLpNieMsForm.celownik);
            result.setDopelniacz(body + PrzymOdmLpNieMsForm.dopelniacz);
        } else {
            body = topic;
            result.setMiejscownik(body + PrzymOdmLpNieMsForm.miejscownik);
            result.setNarzednik(body + PrzymOdmLpNieMsForm.narzednik);
            result.setCelownik(body + PrzymOdmLpNieMsForm.celownik);
            result.setDopelniacz(body + PrzymOdmLpNieMsForm.dopelniacz);
        }
        result.setMianownik(body + PrzymOdmLpNieMsForm.mianownik);
        result.setBiernik(body + PrzymOdmLpNieMsForm.biernik);
        result.setWolacz(body + PrzymOdmLpNieMsForm.wolacz);

        return result;
    }

    private CasesVar genLpZenOs(String topic, String postfix) {
        CasesVar result = new CasesVar(CasesFormQualifier.ADJ_PLUR_F);

        String body;

        if (postfix.equals(Y_TIP)) {
            body = topic.substring(0, topic.lastIndexOf(Y_TIP));
            result.setMianownik(body + PrzymOdmLpZenForm.mianownik);
            result.setBiernik(body + PrzymOdmLpZenForm.biernik);
            result.setNarzednik(body + PrzymOdmLpZenForm.narzednik);
            result.setWolacz(body + PrzymOdmLpZenForm.wolacz);
        } else {
            body = topic;
            result.setMianownik(body.substring(0, body.length() - 1) + PrzymOdmLpZenForm.mianownik);
            result.setBiernik(body.substring(0, body.length() - 1) + PrzymOdmLpZenForm.biernik);
            result.setNarzednik(body.substring(0, body.length() - 1) + PrzymOdmLpZenForm.narzednik);
            result.setWolacz(body.substring(0, body.length() - 1) + PrzymOdmLpZenForm.wolacz);
        }

        result.setDopelniacz(body + PrzymOdmLpZenForm.dopelniacz);
        result.setCelownik(body + PrzymOdmLpZenForm.celownik);
        result.setMiejscownik(body + PrzymOdmLpZenForm.miejscownik);

        return result;
    }

    private CasesVar genLpMsOs(String topic, String postfix) {
        CasesVar result = new CasesVar(CasesFormQualifier.ADJ_SING_M);

        String body;

        if (postfix.equals(Y_TIP)) {
            body = topic.substring(0, topic.lastIndexOf(Y_TIP));
            result.setMianownik(body + Y_TIP);
            result.setWolacz(body + Y_TIP);
            result.setNarzednik(body + PrzymOdmLpMsOsForm.narzednik_y);
            result.setMiejscownik(body + PrzymOdmLpMsOsForm.miejscownik_y);
        } else {
            body = topic;
            result.setMianownik(body);
            result.setWolacz(body);
            result.setNarzednik(body + PrzymOdmLpMsOsForm.narzednik_i);
            result.setMiejscownik(body + PrzymOdmLpMsOsForm.miejscownik_i);
        }

        result.setDopelniacz(body + PrzymOdmLpMsOsForm.dopelniacz);
        result.setCelownik(body + PrzymOdmLpMsOsForm.celownik);
        result.setBiernik(body + PrzymOdmLpMsOsForm.biernik);

        return result;
    }

}
