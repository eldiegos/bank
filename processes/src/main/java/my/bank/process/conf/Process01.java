package my.bank.process.conf;

import my.bank.process.data.*;
import lombok.Getter;
import lombok.Setter;

public class Process01 {

	public static final String PROCESS_KEY = "ID_process_wierd";
	public static final String START_EVENT = "ID_START_EVENT";

	public static final String DO_WIERD_EXTERNAL_TASK = "DO_SOMETHING_WIERD_TOPIC";

	public static final String VAR1 = "var1";
	public static final String VAR2 = "var2";
	public static final String VAR_EXISTS = "exists";
	public static final String VAR_DATUM = "datum";

	Process01() {
	}

	@Getter
	@Setter
	public class ProcessWierdVariables {

		private String var1;
		private String var2;
		private boolean exists;
		private ComplexData datum;

	}

}
