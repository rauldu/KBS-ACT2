package com.company;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import net.sf.clipsrules.jni.*;

public class Main  extends Agent {
    private static Environment clips;

    protected void setup() {
        try {
            clips = new Environment();
        } catch (Exception e) {}

        addBehaviour(new TellBehaviour());
        addBehaviour(new AskBehaviour());
    }
    private class TellBehaviour extends Behaviour {
        boolean tellDone = false;

        public void action() {
            try {
                clips.eval("(clear)");
                clips.eval("(assert (persona tom))");
                clips.build("(deftemplate person (slot name )(slot gender))");
                clips.build("(defrule my-rule1 (person (name ?n)) => (printout t ?n  crlf )))");
                clips.eval("(assert person (name Fred))");
            } catch (Exception e) {}

            tellDone = true;
        }

        public boolean done() {
            if (tellDone) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class AskBehaviour extends Behaviour {
        boolean askDone = false;

        public void action() {
            try {
                clips.eval("(facts)");
                clips.run();
            } catch (Exception e) {}
            askDone = true;
        }

        public boolean done() {
            if (askDone) {
                return true;
            } else {
                return false;
            }
        }

        public int onEnd() {
            return super.onEnd();
        }
    }
}
