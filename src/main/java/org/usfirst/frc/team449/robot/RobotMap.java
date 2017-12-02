package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.wpilibj.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.drive.unidirectional.DriveTalonCluster;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedRunnable;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlCommand;
import org.usfirst.frc.team449.robot.other.Logger;
import org.usfirst.frc.team449.robot.oi.OI;
import org.usfirst.frc.team449.robot.oi.buttons.CommandButton;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SolenoidSimple;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SubsystemSolenoid;
import org.usfirst.frc.team449.robot.subsystem.singleImplementation.pneumatics.Pneumatics;

import java.util.List;

public class RobotMap {

	private List<CommandButton> buttons;

	private SubsystemSolenoid shooter;

	private Pneumatics pneumatics;

	@Nullable
	private final Command startupCommand;

	@NotNull
	private final Logger logger;

	@NotNull
	private final Runnable updater;

	@NotNull
	private final DriveTalonCluster drive;

	@NotNull
	private final OI oi;

	@NotNull
	private final Command defaultDriveCommand;

	@JsonCreator
	public RobotMap(@NotNull List<CommandButton> buttons, @Nullable SubsystemSolenoid shooter, @Nullable Pneumatics pneumatics,
	                @NotNull @JsonProperty(required = true) Logger logger,
	                @NotNull @JsonProperty(required = true) MappedRunnable updater,
	                @NotNull @JsonProperty(required = true) DriveTalonCluster drive,
	                @NotNull @JsonProperty(required = true) OI oi,
	                @NotNull @JsonProperty(required = true) YamlCommand defaultDriveCommand,
	                @Nullable YamlCommand startupCommand){
		this.buttons = buttons;
		this.shooter = shooter;
		this.pneumatics = pneumatics;
		this.logger = logger;
		this.updater = updater;
		this.drive = drive;
		this.oi = oi;
		this.defaultDriveCommand = defaultDriveCommand.getCommand();
		this.startupCommand = startupCommand != null ? startupCommand.getCommand() : null;
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
	public Logger getLogger() {	return logger; }

	@NotNull
	public DriveTalonCluster getDrive() {
		return drive;
	}

	@NotNull
	public OI getOi() {
		return oi;
	}

	@NotNull
	public Command getDefaultDriveCommand() {
		return defaultDriveCommand;
	}

	@Nullable
	public Command getStartupCommand() { return startupCommand; }

	@NotNull
	public Runnable getUpdater() { return updater; }
}