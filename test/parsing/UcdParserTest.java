package parsing;

import org.junit.Test;
import parsing.syntaxTree.entries.DeclarationEntry;
import parsing.syntaxTree.entries.RoleEntry;
import parsing.syntaxTree.exceptions.MalformedFileException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UcdParserTest {

    @Test
    public void extractBetween() {

        String test1 = "a simple <test_start> testing <test_end> to check";

        assertEquals(String.format("testing : %s with tags %s and %s", test1, "<test_start>", "<test_end>"),
                " testing ",
                new UcdParser(test1).extractBetween("<test_start>", "<test_end>"));

        assertEquals(String.format("testing : %s with tags %s and %s", test1, "<", ">"),
                "test_start",
                new UcdParser(test1).extractBetween("<", ">"));

        assertEquals(String.format("testing : %s with tags %s and %s", test1, "s", "m"),
                "i",
                new UcdParser(test1).extractBetween("s", "m"));

        assertEquals(String.format("testing : %s with tags %s and %s", test1, "a", "s"),
                " ",
                new UcdParser(test1).extractBetween("a", "s"));

        // edge case : not there
        assertEquals(String.format("testing : %s with tags %s and %s", test1, "[", "]"),
                "",
                new UcdParser(test1).extractBetween("[", "]"));

        String test2 = "";

        assertEquals("testing : [empty string]",
                "",
                new UcdParser(test2).extractBetween("a", "t"));


    }


    @Test
    public void convertDeclarationEntry() {

        String decTest1 =
                "CLASS Equipe~~nl~~ATTRIBUTES~~nl~~nom_equipe:String~~nl~~OPERATIONS~~nl~~nombre_joueurs():Integer,entraineur():String,add_joueur(element:Joueur):void~~nl~~";
        DeclarationEntry decEntryTest1 = new UcdParser(decTest1).convertDeclarationEntry();

        assertEquals("testing declaration type",
                "CLASS",
                decEntryTest1.decType);

        assertEquals("testing id",
                "Equipe",
                decEntryTest1.id);

        String decTest2 = "CLASS Participant~~nl~~ATTRIBUTES~~nl~~nom:String~~nl~~OPERATIONS~~nl~~";
        DeclarationEntry decEntryTest2 = new UcdParser(decTest2).convertDeclarationEntry();

        assertEquals("testing declaration type",
                "CLASS",
                decEntryTest2.decType);

        assertEquals("testing id",
                "Participant",
                decEntryTest2.id);

        String decTestGen1 = "GENERELIZATION Participant~~nl~~\tSUBCLASSES Joueur, Entraineur~~nl~~";
        DeclarationEntry decEntryTestGen1 = new UcdParser(decTestGen1).convertDeclarationEntry();

        assertEquals("testing declaration type",
                "GENERELIZATION",
                decEntryTestGen1.decType);

        assertEquals("testing id",
                "Participant",
                decEntryTestGen1.id);


    }

    @Test
    public void convertRolesEntry() {

        String testContent1 = "CLASS Equipe MANY";
        RoleEntry entryTest1 = new UcdParser(testContent1).convertRolesEntry("");

        assertEquals("Equipe", entryTest1.classId);
        assertEquals("MANY", entryTest1.multiplicity);
        assertEquals("CLASS", entryTest1.tag);


    }

    @Test(expected = MalformedFileException.class)
    public void convertRolesEntryWrongTag() {

        String testContent1 = "RELATION Equipe MANY";
        RoleEntry entryTest1 = new UcdParser(testContent1).convertRolesEntry("");

        assertEquals("Equipe", entryTest1.classId);
        assertEquals("MANY", entryTest1.multiplicity);
        assertEquals("RELATION", entryTest1.tag);

    }

    @Test(expected = MalformedFileException.class)
    public void convertRolesEntryMalformed() {

        String testContent1 = "RELATIONEquipeMANY";
        RoleEntry entryTest1 = new UcdParser(testContent1).convertRolesEntry("");

        assertEquals("Equipe", entryTest1.classId);
        assertEquals("MANY", entryTest1.multiplicity);
        assertEquals("RELATION", entryTest1.tag);

    }


    @Test
    public void splitAttributeList() {

        // single element
        String test1 = "nom_equipe:String";
        List<String> splits1 = new UcdParser(test1).splitAttributeList();
        List<String> verif1 = new ArrayList<>();
        verif1.add("nom_equipe:String");

        for (int i = 0; i < splits1.size(); i++) {
            assertEquals(splits1.get(i), verif1.get(i));
        }

        // two elements
        String test2 ="saison:Integer,statut:String";
        List<String> splits2 = new UcdParser(test2).splitAttributeList();
        List<String> verif2 = new ArrayList<>();
        verif2.add("saison:Integer");
        verif2.add("statut:String");

        for (int i = 0; i < splits2.size(); i++) {
            assertEquals(splits2.get(i), verif2.get(i));
        }

        // parameterized elements
        String test3 = "test_param1:List<String>, test_param2:Collection<UmlToken>, test_3:ArrayList<Rainbows>";
        List<String> splits3 = new UcdParser(test3).splitAttributeList();
        List<String> verif3 = new ArrayList<>();
        verif3.add("test_param1:List<String>");
        verif3.add("test_param2:Collection<UmlToken>");
        verif3.add("test_3:ArrayList<Rainbows>");

        for (int i = 0; i < splits3.size(); i++) {
            assertEquals(splits3.get(i), verif3.get(i));
        }

        // multiple parameter parameterized elements
        String test4 = "test_param1:Map<String,UmlToken>, test_param2:String, test_3:HashMap<Rainbows,Unicorn>";
        List<String> splits4 = new UcdParser(test4).splitAttributeList();
        List<String> verif4 = new ArrayList<>();
        verif4.add("test_param1:Map<String,UmlToken>");
        verif4.add("test_param2:String");
        verif4.add("test_3:HashMap<Rainbows,Unicorn>");

        for (int i = 0; i < splits4.size(); i++) {
            assertEquals(splits4.get(i), verif4.get(i));
        }

    }

    @Test
    public void splitList() {

        /* DOES NOT KEEP SPACES AFTER COMMA IN PARAMETERIZED TYPE */

        // multiple parameter parameterized elements
        String test1 = "test_param1:Map<String,UmlToken>, test_param2:String, test_3:HashMap<Rainbows,Unicorn>";
        List<String> splits1 = new UcdParser(test1).splitList();
        List<String> verif1 = new ArrayList<>();
        verif1.add("test_param1:Map<String,UmlToken>");
        verif1.add("test_param2:String");
        verif1.add("test_3:HashMap<Rainbows,Unicorn>");

        for (int i = 0; i < splits1.size(); i++) {
            assertEquals(splits1.get(i), verif1.get(i));
        }

        // does not splits between parentheses
        String test2 = "nombre_saisons() : Integer, change_statut(st : String, i : int) : void";
        List<String> splits2 = new UcdParser(test2).splitList();
        List<String> verif2 = new ArrayList<>();
        verif2.add("nombre_saisons() : Integer");
        verif2.add("change_statut(st : String, i : int) : void");

        for (int i = 0; i < splits2.size(); i++) {
            assertEquals(splits2.get(i), verif2.get(i));
        }

        // complex
        String test3 = "nombre_saisons() : Integer[], change_statut(st : String[], i : int, bob : List<Bob>) : Map<String, Unicorn>";
        List<String> splits3 = new UcdParser(test3).splitList();
        List<String> verif3 = new ArrayList<>();
        verif3.add("nombre_saisons() : Integer[]");
        verif3.add("change_statut(st : String[], i : int, bob : List<Bob>) : Map<String,Unicorn>");

        for (int i = 0; i < splits3.size(); i++) {
            assertEquals(splits3.get(i), verif3.get(i));
        }

        // empty edge case
        String test_empty = "";
        List<String> splits_empty = new UcdParser(test_empty).splitList();
        assertEquals(splits_empty.size(),0);

        // almost empty
        String test_empty2 = "  ";
        List<String> splits_empty2 = new UcdParser(test_empty2).splitList();
        assertEquals(splits_empty2.size(),0);



    }


    @Test
    public void extractAttributes() {

        String test1 = "ATTRIBUTES~~nl~~nom_equipe:String~~nl~~OPERATIONS~~nl~~nombre_joueurs():Integer,entraineur():String,add_joueur(element:Joueur):void~~nl~~";
        String res1 = new UcdParser(test1).extractAttributes();
        assertEquals(res1, "~~nl~~nom_equipe:String~~nl~~");

        String test2 = "ATTRIBUTES~~nl~~nom_equipe:String,test:Unicorn~~nl~~OPERATIONS~~nl~~";
        String res2 = new UcdParser(test2).extractAttributes();
        assertEquals(res2, "~~nl~~nom_equipe:String,test:Unicorn~~nl~~");

        String test3 = "ATTRIBUTES nom_equipe:String,test:Unicorn,test2:Rainbows OPERATIONS";
        String res3 = new UcdParser(test3).extractAttributes();
        assertEquals(res3, " nom_equipe:String,test:Unicorn,test2:Rainbows ");

        String test4 = "ATTRIBUTES OPERATIONS";
        String res4 = new UcdParser(test4).extractAttributes();
        assertEquals(res4, " ");

        String test5 = "ATTRIBUTES~~nl~~OPERATIONS";
        String res5 = new UcdParser(test5).extractAttributes();
        assertEquals(res5, "~~nl~~");

    }

    @Test(expected = MalformedFileException.class)
    public void extractAttributesMalformed1() {

        String test1 = "~~nl~~nom_equipe:String~~nl~~OPERATIONS~~nl~~nombre_joueurs():Integer,entraineur():String,add_joueur(element:Joueur):void~~nl~~";
        new UcdParser(test1).extractAttributes();
    }

    @Test(expected = MalformedFileException.class)
    public void extractAttributesMalformed2() {

        String test1 = "ATTRIBUTES~~nl~~nom_equipe:String~~nl~~ATTRIBUTES~~n~~OPERATIONS~~nl~~nombre_joueurs():Integer,entraineur():String,add_joueur(element:Joueur):void~~nl~~";
        new UcdParser(test1).extractAttributes();
    }

    @Test(expected = MalformedFileException.class)
    public void extractAttributesEmptyString() {

        // empty string
        String test1 = "";
        new UcdParser(test1).extractAttributes();
    }



    @Test
    public void extractOperations() {

        String test0 = "ATTRIBUTES~~nl~~nom_equipe:String~~nl~~OPERATIONS~~nl~~nombre_joueurs():Integer~~nl~~";
        String res0 = new UcdParser(test0).extractOperations();
        assertEquals(res0, "~~nl~~nombre_joueurs():Integer~~nl~~");

        String test1 = "ATTRIBUTES~~nl~~nom_equipe:String~~nl~~OPERATIONS~~nl~~nombre_joueurs():Integer,entraineur():String,add_joueur(element:Joueur):void~~nl~~";
        String res1 = new UcdParser(test1).extractOperations();
        assertEquals(res1, "~~nl~~nombre_joueurs():Integer,entraineur():String,add_joueur(element:Joueur):void~~nl~~");

        String test2 = "ATTRIBUTES~~nl~~nom_equipe:String~~nl~~OPERATIONS~~nl~~";
        String res2 = new UcdParser(test2).extractOperations();
        assertEquals(res2, "~~nl~~");

        String test3 = "ATTRIBUTES~~nl~~nom_equipe:String~~nl~~OPERATIONS";
        String res3 = new UcdParser(test3).extractOperations();
        assertEquals(res3, "");

        String test4 = "ATTRIBUTES nom_equipe:String OPERATIONS nombre_joueurs(test : Map<String, Integer>):Integer, entraineur(test : Animal<Unicorn>):String, add_joueur(element:Joueur, test : Unicorn):void";
        String res4 = new UcdParser(test4).extractOperations();
        assertEquals(res4, "nombre_joueurs(test : Map<String, Integer>):Integer, entraineur(test : Animal<Unicorn>):String, add_joueur(element:Joueur, test : Unicorn):void");

        String test5 = "ATTRIBUTES nom_equipe:String OPERATIONS";
        String res5 = new UcdParser(test5).extractOperations();
        assertEquals(res5, "");


    }


    @Test
    public void extractArgList() {

        String test0 = "accept(visitor:UmlVisitor):void";
        String res0 = new UcdParser(test0).extractArgList("");
        assertEquals("testing : " + test0, res0, "visitor:UmlVisitor");

        String test1 = "test_method(visitor:UmlVisitor,test:BigInteger):void";
        String res1 = new UcdParser(test1).extractArgList("");
        assertEquals("testing : " + test1, res1, "visitor:UmlVisitor,test:BigInteger");

        String test2 = "bob(visitor : UmlVisitor, test : BigInteger):void";
        String res2 = new UcdParser(test2).extractArgList("");
        assertEquals("testing : " + test2, res2, "visitor : UmlVisitor, test : BigInteger");

        String test3 = "why(visitors:List<Visitor>):void";
        String res3 = new UcdParser(test3).extractArgList("");
        assertEquals("testing : " + test3, res3, "visitors:List<Visitor>");

        String test4 = "must():void";
        String res4 = new UcdParser(test4).extractArgList("");
        assertEquals("testing : " + test4, res4, "");

        String test5 = "IdoThis( ):void";
        String res5 = new UcdParser(test5).extractArgList("");
        assertEquals("testing : " + test5, res5, "");

        String test6 = "accept      (  visitor:UmlVisitor    ):      void";
        String res6 = new UcdParser(test6).extractArgList("");
        assertEquals("testing : " + test6, res6, "visitor:UmlVisitor");

    }

    @Test(expected = MalformedFileException.class)
    public void extractArgListNoArg1(){
        String test0 = "accept:void";
        new UcdParser(test0).extractArgList("");
    }

    @Test(expected = MalformedFileException.class)
    public void extractArgListNoArg2(){
        String test0 = "";
        new UcdParser(test0).extractArgList("");
    }


    @Test
    public void extractGeneralizationClasses() {

        /* the method removes superflous blank spaces */

        String test0 = "SUBCLASSES UmlClass, UmlOperation, UmlAttribute, UmlAssociation, UmlAggregation";
        String res0 = new UcdParser(test0).extractGeneralizationSubclasses();
        assertEquals("testing : " + test0, res0, "UmlClass,UmlOperation,UmlAttribute,UmlAssociation,UmlAggregation");

        String test1 = "SUBCLASSES     UmlClass, UmlOperation, UmlAttribute, UmlAssociation, UmlAggregation";
        String res1 = new UcdParser(test1).extractGeneralizationSubclasses();
        assertEquals("testing : " + test1, res1, "UmlClass,UmlOperation,UmlAttribute,UmlAssociation,UmlAggregation");

        String test2 = "SUBCLASSES SingleTest";
        String res2 = new UcdParser(test2).extractGeneralizationSubclasses();
        assertEquals("testing : " + test2, res2, "SingleTest");

        String test3 = "SUBCLASSES       SingleTest";
        String res3 = new UcdParser(test3).extractGeneralizationSubclasses();
        assertEquals("testing : " + test3, res3, "SingleTest");

    }

    @Test(expected = MalformedFileException.class)
    public void extractGeneralizationExc1(){
        // new line not allowed, must be a space
        String test2 = "SUBCLASSES~~nl~~UmlClass, UmlOperation, UmlAttribute, UmlAssociation, UmlAggregation";
        new UcdParser(test2).extractGeneralizationSubclasses();

    }

    @Test(expected = MalformedFileException.class)
    public void extractGeneralizationExc2(){
        // empty SUBCLASSES not allowed
        String test4 = "SUBCLASSES ";
        new UcdParser(test4).extractGeneralizationSubclasses();
    }

    @Test(expected = MalformedFileException.class)
    public void extractGeneralizationExc3(){
        // empty SUBCLASSES not allowed
        String test4 = "SUBCLASSES";
        new UcdParser(test4).extractGeneralizationSubclasses();
    }


    @Test(expected = MalformedFileException.class)
    public void extractGeneralizationExc4(){
        // must begin with exact SUBCLASSES TAG
        String test0 = "SUBCLASSE UmlClass, UmlOperation, UmlAttribute, UmlAssociation, UmlAggregation";
        new UcdParser(test0).extractGeneralizationSubclasses();
    }

    @Test(expected = MalformedFileException.class)
    public void extractGeneralizationExcEmpty(){
        String test0 = "";
        new UcdParser(test0).extractGeneralizationSubclasses();
    }




    @Test
    public void extractType() {

        /* the method removes superflous blank spaces */

        String test0 = "accept(visitor:UmlVisitor):void";
        String res0 = new UcdParser(test0).extractType();
        assertEquals("testing : "+test0, res0,"void");

        // we allow extra spaces
        String test1 = "accept(visitor:UmlVisitor) : void";
        String res1 = new UcdParser(test1).extractType();
        assertEquals("testing : "+test1, res1,"void");

        String test2 = "test():Integer";
        String res2 = new UcdParser(test2).extractType();
        assertEquals("testing : "+test2, res2,"Integer");

        String test3 = "accept(visitor:List<UmlVisitor>):void";
        String res3 = new UcdParser(test3).extractType();
        assertEquals("testing : "+test3, res3,"void");

        String test4 = "accept(visitor:List<UmlVisitor>):Collection<UmlToken>";
        String res4 = new UcdParser(test4).extractType();
        assertEquals("testing : "+test4, res4,"Collection<UmlToken>");

        String test5 = "accept(visitor:List<UmlVisitor>) : Map<String, UmlToken>";
        String res5 = new UcdParser(test5).extractType();
        assertEquals("testing : "+test5, res5,"Map<String,UmlToken>");


    }

    @Test(expected = MalformedFileException.class)
    public void extractTypeMalformed1(){
        // requires ":"
        String test0 = "accept(visitor:UmlVisitor) void";
        new UcdParser(test0).extractType();
    }

    @Test(expected = MalformedFileException.class)
    public void extractTypeMalformed2(){
        // requires "(...):"
        String test0 = "accept visitor:UmlVisitor : void";
        new UcdParser(test0).extractType();
    }

    @Test(expected = MalformedFileException.class)
    public void extractTypeMalformedEmpty(){
        String test0 = "";
        new UcdParser(test0).extractType();
    }


    @Test
    public void extractcParts() {

        String test0 = "CONTAINER~~nl~~CLASS UmlContext ONE~~nl~~PARTS~~nl~~CLASS UmlClass ONE_OR_MANY,";
        String res0 = new UcdParser(test0).extractcParts();
        assertEquals("testing : "+test0, res0, "CLASS UmlClass ONE_OR_MANY,");

        String test1=
                "CONTAINER~~nl~~CLASS UmlClass ONE~~nl~~PARTS~~nl~~CLASS UmlOperation ONE_OR_MANY," +
                        "CLASS UmlAttribute ONE_OR_MANY," +
                        "CLASS UmlAggregation ONE_OR_MANY," +
                        "CLASS UmlAssociation ONE_OR_MANY~~nl~~";
        String res1 = new UcdParser(test1).extractcParts();
        assertEquals("testing : "+test1, res1,
                "CLASS UmlOperation ONE_OR_MANY," +
                        "CLASS UmlAttribute ONE_OR_MANY," +
                        "CLASS UmlAggregation ONE_OR_MANY," +
                        "CLASS UmlAssociation ONE_OR_MANY");

    }



}