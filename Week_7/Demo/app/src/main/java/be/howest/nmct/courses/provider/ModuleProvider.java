package be.howest.nmct.courses.provider;

import java.util.ArrayList;
import java.util.List;

import be.howest.nmct.courses.model.Module;
import be.howest.nmct.courses.model.Docent;

/**
 * Created by Stijn on 23/03/2015.
 */
public class ModuleProvider {

    private static List<Module> modules2NMCT;
    static {
        modules2NMCT = new ArrayList<>();

        Module webAdv2NMCT = new Module("Web Advanced",3);
        webAdv2NMCT.getDocentList().add(new Docent("De Weggheleire","Koen","Koen.De.Weggheleire@howest.be"));
        webAdv2NMCT.getDocentList().add(new Docent("DerRudder","Kevin","Kevin.DeRudder@howest.be"));


        Module appDev2NMCT = new Module("Mobile App Development",4);
        appDev2NMCT.getDocentList().add(new Docent("Verborgh","Steven","Steven.Verborgh@howest.be"));
        appDev2NMCT.getDocentList().add(new Docent("Walcarius","Stijn",""));
        appDev2NMCT.getDocentList().add(new Docent("Daels","Jef","Jef.Daels@howest.be"));

        Module newMedia2NMCT = new Module("New Media",4);
        newMedia2NMCT.getDocentList().add(new Docent("DeRudder","Kevin","Kevin.DeRudder@howest.be"));
        newMedia2NMCT.getDocentList().add(new Docent("Fallein","Angelo","Angelo.Fallein@howest.be"));

        Module dataCenterTechnology = new Module("Datacenter Technology",4);
        dataCenterTechnology.getDocentList().add(new Docent("De Gelas","Johan","Johan.DeGelas@howest.be"));
        dataCenterTechnology.getDocentList().add(new Docent("Deneut","Tijl","Tijl.Deneut@howest.be"));

        Module bap2NMCT = new Module("Business Applications",3);
        bap2NMCT.getDocentList().add(new Docent("Duchi","Frederik","Frederik.Duchi@howest.be"));
        bap2NMCT.getDocentList().add(new Docent("Bostyn","Henk","Henk.Bostyn@howest.be"));


        Module datacommunication  = new Module("Datacommunication",4);
        datacommunication.getDocentList().add(new Docent("Bostyn","Henk","Henk.Bostyn@howest.be"));
        datacommunication.getDocentList().add(new Docent("Desloovere", "Geert", "Geert.Desloovere@howest.be"));
        datacommunication.getDocentList().add(new Docent("Gevaert", "Wouter", "Wouter.Gevaert@howest.be"));

        modules2NMCT.add(webAdv2NMCT);
        modules2NMCT.add(appDev2NMCT);
        modules2NMCT.add(newMedia2NMCT);
        modules2NMCT.add(dataCenterTechnology);
        modules2NMCT.add(bap2NMCT);
        modules2NMCT.add(datacommunication);
    }


    public static List<Module> getModules2NMCT() {
        return modules2NMCT;
    }

    public static List<Docent> getDocentenModule(String sModulenaam){
        List<Docent> docenten = new ArrayList<>();
        for(Module module : modules2NMCT){
            if (module.getNaam().equals(sModulenaam)){
                docenten.addAll(module.getDocentList());
            }
        }
        return docenten;
    }
}
