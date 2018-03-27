package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.PersonVar;
import com.mindmap.jane.domain.VerbVar;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.domain.enumeration.PersonVarTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mindmap.jane.utils.WikiStringUtils.*;

@Service
//TODO rewrite to regex verify with current wiktionary template
public class VerbVarParser {

    private static final Logger log = LoggerFactory.getLogger(VerbVarParser.class);

    public VerbVar parse(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return null;
        }

        VerbVar verVar = new VerbVar();
        List<String> variety = new ArrayList<>();

        boolean simpleForm = false; // FLAGA PRZECHOWUJĄCA INFORMACJE O TYPIE
        // FORMULARZA ODMIANY.

        // USUŃ ZBĘDNE DANE.
        for (String s : dataBlock) {
            variety.add(removeAdditionalComment(removeNumeration(removeReferences(s))));
        }

        if (dataBlock.get(0).contains("nieodm") || dataBlock.get(0).contains("zob")) {
            return verVar;
        }

        for (String str : variety) {
            if (str.contains("{{KoniugacjaPL|"))
                simpleForm = true;
        }

        if (simpleForm) {
            parserSimpleForm(verVar, variety, wikiUnit);
        } else {
            parseNormalForm(verVar, variety);
        }

        // print("Odmiana czasownika: " + czasOdm);

        return verVar;
    }

    private void parserSimpleForm(VerbVar verbVar, List<String> variety,
                                  WikiUnit tempUnit) {
        for (String line : variety) {
            String whiteSpaceSplited[];

            whiteSpaceSplited = line.split("\\s");

            for (int i = 0; i < whiteSpaceSplited.length; i++) {
                if (tempUnit.getName().equals("kupować")) {
                    log.debug(whiteSpaceSplited[i]);
                }

                if (whiteSpaceSplited[i].contains("|")
                    && !whiteSpaceSplited[i].contains("oniugacja")) {
                    String topicAndTip[] = whiteSpaceSplited[i].split("\\|");
                    if (topicAndTip.length > 0) {
                        verbVar.setTopic(removePunctuation(topicAndTip[0]));
                    }
                }

                if (whiteSpaceSplited[i].contains("oniugacja")) {
                    String temp = removePunctuation(removeParenthesis(whiteSpaceSplited[i]));
                    String conjugation[] = temp.split("\\|");
                    if (conjugation.length > 1) {
                        verbVar.setConjugation(conjugation[1]);
                    }

                }

                if (whiteSpaceSplited[i].contains("{{dk}}")) {
                    // TODO DODAĆ OBSŁUGĘ TRYBU DOKONANEGO W POSTACI {{dk}} LUB
                    // TEKSTOWO NP: ''aspekt dokonany''
                    // treść.
                    if (whiteSpaceSplited.length > (i + 1)) {
                        verbVar.setPerfective(removeParenthesis(whiteSpaceSplited[i + 1]));
                    }
                }

                if (whiteSpaceSplited[i].contains("{{ndk}}")) {
                    if (whiteSpaceSplited.length > (i + 1)) {
                        verbVar.setImperfective(removeParenthesis(whiteSpaceSplited[i + 1]));
                    }
                }
                // debug
                if (tempUnit.getName().equals("kupować")) {
                    log.debug("temat: " + verbVar.getTopic() + " koniugacja: "
                        + verbVar.getConjugation());
                }
            }

            verbVar.fillConjugationVariety();
        }
    }

    private void parseNormalForm(VerbVar verbVar, List<String> veriaty) {
        PersonVar czasTeraz = new PersonVar(PersonVarTypeEnum.CZAS_TERAZ);
        PersonVar czasPrzeszlyM = new PersonVar(PersonVarTypeEnum.CZAS_PRZESZ_M); // Czas
        // przeszły
        PersonVar czasPrzeszlyF = new PersonVar(PersonVarTypeEnum.CZAS_PRZESZ_F);
        PersonVar czasPrzeszlyN = new PersonVar(PersonVarTypeEnum.CZAS_PRZESZ_N);
        PersonVar czasPrzyszlyM = new PersonVar(PersonVarTypeEnum.CZAS_PRZYSZ_M);
        PersonVar czasPrzyszlyF = new PersonVar(PersonVarTypeEnum.CZAS_PRZYSZ_F);
        PersonVar czasPrzyszlyN = new PersonVar(PersonVarTypeEnum.CZAS_PRZYSZ_N);
        PersonVar trybRozkaz = new PersonVar(PersonVarTypeEnum.TRYB_ROZKAZ); // Tryb
        PersonVar trybPrzypM = new PersonVar(PersonVarTypeEnum.TRYB_PRZYP_M); // Tryb
        // przypuszczający
        PersonVar trybPrzypF = new PersonVar(PersonVarTypeEnum.TRYB_PRZYP_F);
        PersonVar trybPrzypN = new PersonVar(PersonVarTypeEnum.TRYB_PRZYP_N);
        PersonVar imiesPrzymCzynnyM = new PersonVar(
            PersonVarTypeEnum.IMIES_PRZYM_CZYNNY_M); // Imiesłów przymiotnikowy
        // czynny
        PersonVar imiesPrzymCzynnyF = new PersonVar(
            PersonVarTypeEnum.IMIES_PRZYM_CZYNNY_F);
        PersonVar imiesPrzymCzynnyN = new PersonVar(
            PersonVarTypeEnum.IMIES_PRZYM_CZYNNY_N);
        PersonVar imiesPrzymBiernyM = new PersonVar(
            PersonVarTypeEnum.IMIES_PRZYM_BIERNY_M); // Imiesłów przymiotnikowy
        // bierny
        PersonVar imiesPrzymBiernyF = new PersonVar(
            PersonVarTypeEnum.IMIES_PRZYM_BIERNY_F);
        PersonVar imiesPrzymBiernyN = new PersonVar(
            PersonVarTypeEnum.IMIES_PRZYM_BIERNY_N);

        for (String line : veriaty) {

            String type = getStringBeforeEqual(line);

            // print("linia:" + line + " typ: " + typ);

            if (type.equals("dokonany")) {
                String dk = getStringAfterEqual(line);
                if (dk != null && dk.equals("tak")) {
                    verbVar.setIsPerfective(true);
                }
            }

            if (type.equals("się")) {
                verbVar.setIsReflexivVerb(true);
                verbVar.setReflexivPronoun(getStringAfterEqual(line));
            }

            if (type.equals("koniugacja")) {
                verbVar.setConjugation(getStringAfterEqual(line));
            }
            if (type.equals("robić") || type.equals("zrobić")) {
                verbVar.setInfinitive(getStringAfterEqual(line));
            }
            if (type.equals("robię") || type.equals("zrobię")) {
                czasTeraz.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("robi") || type.equals("zrobi")) {
                czasTeraz.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("robisz") || type.equals("zrobisz")) {
                czasTeraz.setPer2sing(getStringAfterEqual(line));
            }
            if (type.equals("robią") || type.equals("zrobią")) {
                czasTeraz.setPer3plur(getStringAfterEqual(line));
            }
            if (type.equals("robimy") || type.equals("zrobimy")) {
                czasTeraz.setPer1plur(getStringAfterEqual(line));
            }
            if (type.equals("robicie") || type.equals("zrobicie")) {
                czasTeraz.setPer2plur(getStringAfterEqual(line));
            }
            if (type.equals("robiłem") || type.equals("zrobiłem")) {
                czasPrzeszlyM.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("robił") || type.equals("zrobił")) {
                czasPrzeszlyM.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("robiła") || type.equals("zrobiła")) {
                czasPrzeszlyF.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("robili") || type.equals("zrobię")) {
                czasPrzeszlyM.setPer3plur(getStringAfterEqual(line));
            }
            if (type.equals("robiono") || type.equals("zrobiono")) {
                czasPrzeszlyN.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("będęrobił") || type.equals("będęzrobił")) {
                czasPrzyszlyM.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("będęrobiła") || type.equals("będęzrobiła")) {
                czasPrzyszlyF.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("będęrobiło") || type.equals("będęzrobiło")) {
                czasPrzyszlyN.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("będzieszrobił") || type.equals("będzieszzrobił")) {
                czasPrzyszlyM.setPer2sing(getStringAfterEqual(line));
            }
            if (type.equals("będzieszrobiła") || type.equals("będzieszzrobiła")) {
                czasPrzyszlyF.setPer2sing(getStringAfterEqual(line));
            }
            if (type.equals("będzieszrobiło") || type.equals("będzieszzrobiło")) {
                czasPrzyszlyN.setPer2sing(getStringAfterEqual(line));
            }
            if (type.equals("będzierobił") || type.equals("będziezrobił")) {
                czasPrzyszlyM.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("będzierobiła") || type.equals("będziezrobiła")) {
                czasPrzyszlyF.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("będzierobiło") || type.equals("będziezrobiła")) {
                czasPrzyszlyN.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("będziemyrobili") || type.equals("będziemyzrobili")) {
                czasPrzyszlyM.setPer1plur(getStringAfterEqual(line));
            }
            if (type.equals("będziemyrobiły") || type.equals("będziemyzrobiły")) {
                czasPrzyszlyF.setPer1plur(getStringAfterEqual(line));
                czasPrzyszlyN.setPer1plur(getStringAfterEqual(line));
            }
            if (type.equals("będziecierobili")
                || type.equals("będzieciezrobili")) {
                czasPrzyszlyM.setPer2plur(getStringAfterEqual(line));
            }
            if (type.equals("będziecierobiły")
                || type.equals("będzieciezrobiły")) {
                czasPrzyszlyF.setPer2plur(getStringAfterEqual(line));
                czasPrzyszlyN.setPer2plur(getStringAfterEqual(line));
            }
            if (type.equals("będąrobili") || type.equals("będązrobili")) {
                czasPrzyszlyM.setPer3plur(getStringAfterEqual(line));
            }
            if (type.equals("będąrobiły") || type.equals("będązrobiły")) {
                czasPrzyszlyF.setPer3plur(getStringAfterEqual(line));
            }
            if (type.equals("robiłbym") || type.equals("zrobiłbym")) {
                trybPrzypM.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłbyś") || type.equals("zrobiłbyś")) {
                trybPrzypM.setPer2sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłby") || type.equals("zrobiłby")) {
                trybPrzypM.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("robilibyśmy") || type.equals("zrobilibyśmy")) {
                trybPrzypM.setPer1plur(getStringAfterEqual(line));
            }
            if (type.equals("robilibyście") || type.equals("zrobilibyście")) {
                trybPrzypM.setPer2plur(getStringAfterEqual(line));
            }
            if (type.equals("robiliby") || type.equals("zrobiliby")) {
                trybPrzypM.setPer3plur(getStringAfterEqual(line));
            }
            if (type.equals("robiłabym") || type.equals("zrobiłabym")) {
                trybPrzypF.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłabyś") || type.equals("zrobiłabyś")) {
                trybPrzypF.setPer2sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłaby") || type.equals("zrobiłaby")) {
                trybPrzypF.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłobym") || type.equals("zrobiłobym")) {
                trybPrzypN.setPer1sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłobyś") || type.equals("zrobiłobyś")) {
                trybPrzypN.setPer2sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłoby") || type.equals("zrobiłoby")) {
                trybPrzypN.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("robiłybyśmy") || type.equals("zrobiłybyśmy")) {
                trybPrzypF.setPer1plur(getStringAfterEqual(line));
                trybPrzypN.setPer1plur(getStringAfterEqual(line));
            }
            if (type.equals("robiłybyście") || type.equals("zrobiłybyście")) {
                trybPrzypF.setPer2plur(getStringAfterEqual(line));
                trybPrzypN.setPer2plur(getStringAfterEqual(line));
            }
            if (type.equals("robiłby") || type.equals("zrobiłby")) {
                trybPrzypF.setPer3plur(getStringAfterEqual(line));
                trybPrzypN.setPer3plur(getStringAfterEqual(line));
            }
            if (type.equals("niechrobi") || type.equals("niechzrobi")) {
                trybRozkaz.setPer3sing(getStringAfterEqual(line));
            }
            if (type.equals("niechrobią") || type.equals("niechzrobią")) {
                trybRozkaz.setPer3plur(getStringAfterEqual(line));
            }
            if (type.equals("robiono") || type.equals("zrobiono")) {
            }
            if (type.equals("rób") || type.equals("zrób")) {
                trybRozkaz.setPer2sing(getStringAfterEqual(line));
            }

            if (type.equals("robiąc") || type.equals("zrobiąc")) {
            }
            if (type.equals("zrobiwszy")) {
            }
            if (type.equals("robienie") || type.equals("zrobienie")) {
            }
            if (type.equals("brakzaprzeszłego")) {
            }
        }
        if (varNotEmpty(czasTeraz)) {
            verbVar.addPersonVar(czasTeraz);
        }

        if (varNotEmpty(czasPrzeszlyM)) {
            verbVar.addPersonVar(czasPrzeszlyM);
        }

        if (varNotEmpty(czasPrzeszlyF)) {
            verbVar.addPersonVar(czasPrzeszlyF);
        }

        if (varNotEmpty(czasPrzeszlyN)) {
            verbVar.addPersonVar(czasPrzeszlyN);
        }

        if (varNotEmpty(czasPrzyszlyM)) {
            verbVar.addPersonVar(czasPrzyszlyM);
        }

        if (varNotEmpty(czasPrzyszlyF)) {
            verbVar.addPersonVar(czasPrzyszlyF);
        }

        if (varNotEmpty(czasPrzyszlyN)) {
            verbVar.addPersonVar(czasPrzyszlyN);
        }

        if (varNotEmpty(trybRozkaz)) {
            verbVar.addPersonVar(trybRozkaz);
        }

        if (varNotEmpty(trybPrzypM)) {
            verbVar.addPersonVar(trybPrzypM);
        }

        if (varNotEmpty(trybPrzypN)) {
            verbVar.addPersonVar(trybPrzypN);
        }

        if (varNotEmpty(trybPrzypF)) {
            verbVar.addPersonVar(trybPrzypF);
        }

        if (varNotEmpty(imiesPrzymCzynnyM)) {
            verbVar.addPersonVar(imiesPrzymCzynnyM);
        }

        if (varNotEmpty(imiesPrzymCzynnyF)) {
            verbVar.addPersonVar(imiesPrzymCzynnyF);
        }

        if (varNotEmpty(imiesPrzymCzynnyN)) {
            verbVar.addPersonVar(imiesPrzymCzynnyN);
        }

        if (varNotEmpty(imiesPrzymBiernyM)) {
            verbVar.addPersonVar(imiesPrzymBiernyM);
        }

        if (varNotEmpty(imiesPrzymBiernyF)) {
            verbVar.addPersonVar(imiesPrzymBiernyF);
        }

        if (varNotEmpty(imiesPrzymBiernyN)) {
            verbVar.addPersonVar(imiesPrzymBiernyN);
        }
    }

    private boolean varNotEmpty(PersonVar var) {
        return var.getPer1plur() != null ||
            var.getPer1sing() != null ||
            var.getPer2plur() != null ||
            var.getPer2sing() != null ||
            var.getPer3plur() != null ||
            var.getPer3sing() != null;
    }

}
