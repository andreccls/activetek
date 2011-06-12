package co.com.activetek.aclocking.world;
import java.util.ArrayList;

import co.com.activetek.aclocking.entitybeans.Employee;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationControl;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationEvent;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationListener;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationVetoException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

public class VerificationThread 
{

	/**
	 * Enrollment control test
	 */
	private ArrayList<Employee> employees;
	private int farRequested;
	private int farAchieved;
	private DPFPVerificationControl verificationControl;
	private boolean matched;

	static final String FAR_PROPERTY = "FAR";
	static final String MATCHED_PROPERTY = "Matched";

	public VerificationThread(ArrayList<Employee> emp, int farRequested) {

		employees=emp;
		this.farRequested = farRequested;

		verificationControl = new DPFPVerificationControl();
		verificationControl.addVerificationListener(new DPFPVerificationListener()
		{
			public void captureCompleted(DPFPVerificationEvent e) throws DPFPVerificationVetoException
			{
				final DPFPVerification verification = 
					DPFPGlobal.getVerificationFactory().createVerification(VerificationThread.this.farRequested);
				e.setStopCapture(false);	// we want to continue capture until the dialog is closed
				int bestFAR = DPFPVerification.PROBABILITY_ONE;
				boolean hasMatch = false;
				for(Employee empl:VerificationThread.this.employees)
				{
					for (DPFPTemplate template : empl.getTemplates().values()) 
					{
						final DPFPVerificationResult result = verification.verify(e.getFeatureSet(), template);
						e.setMatched(result.isVerified());		// report matching status
						bestFAR = Math.min(bestFAR, result.getFalseAcceptRate());
						if (e.getMatched()) {
							hasMatch = true;
							System.out.println("El empleado en verificación es: "+empl.getNombre());
							break;
						}
					}
					if(hasMatch)
						break;
				}
				if(!hasMatch)
					System.out.println("La persona en el lector no está registrada en la base de datos");
				setMatched(hasMatch);
				setFAR(bestFAR);
			}
		});

	}

	public void start()
	{
		verificationControl.start();
		System.out.println("Monitoring Thread Up");
	}
	public void stop()
	{
		verificationControl.stop();
		System.out.println("Monitoring Thread Down");
	}

	public int getFAR() {
		return farAchieved;
	}

	protected void setFAR(int far) {
		farAchieved = far;
		System.out.println(""+farAchieved);
	}

	public boolean getMatched() {
		return matched;
	}

	protected void setMatched(boolean matched) {
		this.matched = matched;
		System.out.println(""+matched);
	}
}