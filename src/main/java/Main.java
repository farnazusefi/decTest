import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {

	public static void main(String[] args) throws IOException {

		String fileName = "mtc";
		BuchiAutomata inputBuchiAutomata = constructBuchiAutomataFromFile(fileName);
		PropertyDecomposer decomposer = new PropertyDecomposer();
		decomposer.decomposeBuchiAutomata(inputBuchiAutomata);
		BuchiAutomata livenessBuchiAutomata = decomposer.getLivenessBuchiAutomata();
		BuchiAutomata safetyBuchiAutomata = decomposer.getSafetyBuchiAutomata();
	}

	private static BuchiAutomata constructBuchiAutomataFromFile(String fileName) throws IOException {

		BuchiAutomata buchiAutomata = new BuchiAutomata();

		RandomAccessFile file = new RandomAccessFile(fileName, "r");
		String line;
		while ((line = file.readLine()) != null) {

			String[] arrOfStr = line.split("--");
			String fromStateLabel = arrOfStr[0];
			String toStateLabel = arrOfStr[2];
			String transitionLabel = arrOfStr[1];
			State fromState;
			State toState;
			if (fromStateLabel.contains("[init]")) {
				fromStateLabel = fromStateLabel.replace("[init]", "");
				if (buchiAutomata.hasState(fromStateLabel))
					fromState = buchiAutomata.getState(fromStateLabel);
				else {
					fromState = new State(fromStateLabel);
					fromState.setType(StateType.INIT);
					buchiAutomata.addState(fromState);
				}
			} else if (fromStateLabel.contains("[final]")) {
				fromStateLabel = fromStateLabel.replace("[final]", "");
				if (buchiAutomata.hasState(fromStateLabel))
					fromState = buchiAutomata.getState(fromStateLabel);
				else {
					fromState = new State(fromStateLabel);
					fromState.setType(StateType.FINAL);
					buchiAutomata.addState(fromState);
				}
			} else {
				if (buchiAutomata.hasState(fromStateLabel))
					fromState = buchiAutomata.getState(fromStateLabel);
				else {
					fromState = new State(fromStateLabel);
					buchiAutomata.addState(fromState);
				}
			}if (toStateLabel.contains("[init]")) {
				toStateLabel = toStateLabel.replace("[init]", "");
				if (buchiAutomata.hasState(toStateLabel))
					toState = buchiAutomata.getState(toStateLabel);
				else {
					toState = new State(toStateLabel);
					toState.setType(StateType.INIT);
					buchiAutomata.addState(toState);
				}
			}
			else if (toStateLabel.contains("[final]")) {
				toStateLabel = toStateLabel.replace("[final]", "");
				if (buchiAutomata.hasState(toStateLabel))
					toState = buchiAutomata.getState(toStateLabel);
				else {
					toState = new State(toStateLabel);
					toState.setType(StateType.FINAL);
					buchiAutomata.addState(toState);
				}
			}else {
				if (buchiAutomata.hasState(toStateLabel))
					toState = buchiAutomata.getState(toStateLabel);
				else {
					toState = new State(toStateLabel);
					buchiAutomata.addState(toState);
				}
			}
			Transition transition = new Transition(transitionLabel, toState);
			fromState.addTransition(transition);
		}
		file.close();

		return buchiAutomata;
	}

}