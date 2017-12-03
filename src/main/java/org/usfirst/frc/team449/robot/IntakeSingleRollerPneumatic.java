package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.annotation.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.generalInterfaces.simpleMotor.SimpleMotor;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedDoubleSolenoid;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlSubsystem;
import org.usfirst.frc.team449.robot.subsystem.interfaces.intake.SubsystemIntake;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SubsystemSolenoid;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.CLASS,
		include = JsonTypeInfo.As.WRAPPER_OBJECT,
		property = "@class"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class IntakeSingleRollerPneumatic extends YamlSubsystem implements SubsystemSolenoid, SubsystemIntake {

	@NotNull
	private DoubleSolenoid actuator;

	@NotNull
	private SimpleMotor roller;

	private double intakeVelocityFast;
	private double intakeVelocitySlow;

	private IntakeMode intakeMode;

	@JsonCreator
	public IntakeSingleRollerPneumatic(@JsonProperty(required = true) MappedDoubleSolenoid actuator,
	                                   @JsonProperty(required = true) SimpleMotor roller,
	                                   double intakeVelocityFast,
	                                   double intakeVelocitySlow) {
		this.actuator = actuator;
		this.roller = roller;
		this.intakeVelocityFast = intakeVelocityFast;
		this.intakeVelocitySlow = intakeVelocitySlow;
	}

	@Override
	protected void initDefaultCommand() {
	}

	@NotNull
	@Override
	public IntakeMode getMode() {
		return intakeMode;
	}

	@Override
	public void setMode(@NotNull IntakeMode intakeMode) {
		this.intakeMode = intakeMode;
		switch (intakeMode){
			case OFF:
				roller.disable();
				break;
			case IN_FAST:
				roller.enable();
				roller.setVelocity(intakeVelocityFast);
				break;
			case IN_SLOW:
				roller.enable();
				roller.setVelocity(intakeVelocitySlow);
				break;
			case OUT_FAST:
				roller.enable();
				roller.setVelocity(-intakeVelocityFast);
				break;
			case OUT_SLOW:
				roller.enable();
				roller.setVelocity(-intakeVelocitySlow);
				break;
		}
	}

	@Override
	public void setSolenoid(@NotNull DoubleSolenoid.Value value) {
		actuator.set(value);
	}

	@NotNull
	@Override
	public DoubleSolenoid.Value getSolenoidPosition() {
		return actuator.get();
	}
}
