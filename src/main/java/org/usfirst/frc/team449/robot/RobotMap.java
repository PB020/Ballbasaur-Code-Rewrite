package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.wpilibj.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.drive.unidirectional.DriveTalonCluster;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlCommand;
import org.usfirst.frc.team449.robot.oi.buttons.CommandButton;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SolenoidSimple;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SubsystemSolenoid;
import org.usfirst.frc.team449.robot.subsystem.singleImplementation.pneumatics.Pneumatics;

import java.util.List;

public class RobotMap {

	private List<CommandButton> buttons;

	private SubsystemSolenoid shooter;

	private Pneumatics pneumatics;

	@NotNull
	private final DriveTalonCluster drive;

	@NotNull
	private final Command defaultDriveCommand;

	@JsonCreator
	public RobotMap(@NotNull List<CommandButton> buttons, @Nullable SubsystemSolenoid shooter, @Nullable Pneumatics pneumatics,
	                @NotNull @JsonProperty(required = true) DriveTalonCluster drive,
	                @NotNull @JsonProperty(required = true) YamlCommand defaultDriveCommand){
		this.buttons = buttons;
		this.shooter = shooter;
		this.pneumatics = pneumatics;
		this.drive = drive;
		this.defaultDriveCommand = defaultDriveCommand.getCommand();

	}

	public List<CommandButton> getButtons() {
		return buttons;
	}

	public SubsystemSolenoid getShooter() {
		return shooter;
	}

	public Pneumatics getPneumatics() {
		return pneumatics;
	}

	@NotNull
	public DriveTalonCluster getDrive() {
		return drive;
	}

	@NotNull
	public Command getDefaultDriveCommand() {
		return defaultDriveCommand;
	}
}