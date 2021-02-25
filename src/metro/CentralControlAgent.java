package metro;


import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import metro.gui.TASS;
import metro.extras.Ansi;

import java.util.ArrayList;

/*
    Ideia deste agente seria estar conectado a uma sala de controle central.
    Determinar paragens totais, partidas, estacoes ativas etc..

 */

public class CentralControlAgent extends Agent{

    public TASS myGui;
    private AgentController t1 = null;
    private ArrayList<String> stationTrackList = new ArrayList<>();

    protected void setup() {
        System.out.println(new Ansi(Ansi.ITALIC, Ansi.BLUE).format("Central Agent: ") +
                "Central Control Agent " + this.getAID().getName() + " is ready.");
        this.myGui = new TASS("Central Train Station Control System",this);
        this.myGui.showGui();
        // Cria estacao padrao
        this.createAgent("st1", "metro.StationAgent", new String[]{"1"});
        this.createAgent("st2", "metro.StationAgent", new String[]{"2"});
        this.createAgent("st3", "metro.StationAgent", new String[]{"3"});
        // Adciona a ordem das Estacoes a ser usada pelos comboios
        stationTrackList.add("st1"+"@metro.StationAgent");
        stationTrackList.add("st2"+"@metro.StationAgent");
        stationTrackList.add("st2"+"@metro.StationAgent");

        /*
         *   Recebe mensagens de TrainAgents
         *   MatchPerformative(ACLMessage.INFORM)
         */
        addBehaviour(new metro.behaviors.central.ReceiveReport(myGui));

    }
    public void createAgent(String name,String className,Object[]args){

        try {
            AgentContainer container = this.getContainerController();
            this.t1 = container.createNewAgent(name, className, args);
            this.t1.start();
            System.out.println(new Ansi(Ansi.ITALIC, Ansi.BLUE).format("Central Agent: ") +
                    "Initializing Agent "+ name + "@" + className);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
