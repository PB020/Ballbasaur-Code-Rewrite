package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.drive.unidirectional.DriveTalonCluster;
import org.usfirst.frc.team449.robot.other.Clock;
import org.usfirst.frc.team449.robot.other.Logger;
import org.usfirst.frc.team449.robot.subsystem.interfaces.motionProfile.commands.RunLoadedProfile;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The main class of the robot, constructs all the subsystems and initializes default commands.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class Robot extends IterativeRobot {

	/**
	 * The absolute filepath to the resources folder containing the config files.
	 */
	@NotNull
	public static final String RESOURCES_PATH = "/home/lvuser/449_resources/";

	/**
	 * The object constructed directly from the yaml map.
	 */
	private RobotMap robotMap;

	/**
	 * Determines if the robot on.
	 */
	private boolean enabled;

	/**
	 * Um... Logs the output
	 */
	private Logger logger;

	/**
	 * Um... The Drive
	 */
	private DriveTalonCluster driveSubsystem;

	/**
	 * The Notifier running the logging thread.
	 */
	private Notifier loggerNotifier;

	/**
	 * The method that runs when the robot is turned on. Initializes all subsystems from the map.
	 */
	public void robotInit() {
		//Set up start time
		Clock.setStartTime();
		Clock.updateTime();

		enabled = false;

		//Yes this should be a print statement, it's useful to know that robotInit started.
		System.out.println("Started robotInit.");
		Yaml yaml = new Yaml();
		try {
			Map<?, ?> normalized = (Map<?, ?>) yaml.load(new FileReader(RESOURCES_PATH+"ballbasaur_map.yml"));
			YAMLMapper mapper = new YAMLMapper();
			String fixed = mapper.writeValueAsString(normalized);
			mapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
			robotMap = mapper.readValue(fixed, RobotMap.class);
		} catch (IOException e) {
			System.out.println("Config file is bad/nonexistent!");
			e.printStackTrace();
		}

		//Read sensors
		this.robotMap.getUpdater().run();

		this.loggerNotifier = new Notifier(robotMap.getLogger());
		this.driveSubsystem = robotMap.getDrive();

		//Run the logger to write all the events that happened during initialization to a file.
//		robotMap.getLogger().run();
		Clock.updateTime();
	}

	 //Run when we first enable in teleop.
	@Override
	public void teleopInit() {
		//Read sensors
		this.robotMap.getUpdater().run();

		//Enables the robot
		if (!enabled) {
			if (robotMap.getStartupCommand() != null) {
				robotMap.getStartupCommand().start();
			}
			enabled = true;
		}

		//Set the default command
		driveSubsystem.setDefaultCommandManual(robotMap.getDefaultDriveCommand());

	}

	/**
	 * Run every tick in teleop.
	 */
	@Override
	public void teleopPeriodic() {
		//Refresh the current time.
		Clock.updateTime();
		//Read sensors
		this.robotMap.getUpdater().run();
		//Run all commands. This is a WPILib thing you don't really have to worry about.
		Scheduler.getInstance().run();
	}

	/**
	 * Run when we first enable in autonomous
	 */
	@Override
	public void autonomousInit() {
	}


	/**
	 * Runs every tick in autonomous.

	@Override
	public void autonomousPeriodic() {
		//Update the current time
		Clock.updateTime();
		//Run all commands. This is a WPILib thing you don't really have to worry about.
		Scheduler.getInstance().run();
	}*/

	/**
	 * Run when we disable.
	 */
	@Override
	public void disabledInit() {
	}

	/**
	 * Run when we first enable in test mode.

	@Override
	public void testInit() {
	}*/

	/**
	 * Run every tic while disabled
	@Override
	public void disabledPeriodic() {
		Clock.updateTime();
	}*/

	/**
	 * Sends the current mode (auto, teleop, or disabled) over I2C.
	 *
	 * @param i2C  The I2C channel to send the data over.
	 * @param mode The current mode, represented as a String.

	private void sendModeOverI2C(I2C i2C, String mode) {
		//If the I2C exists
		if (i2C != null) {
			//Turn the alliance and mode into a character array.
			char[] CharArray = (allianceString + "_" + mode).toCharArray();
			//Transfer the character array to a byte array.
			byte[] WriteData = new byte[CharArray.length];
			for (int i = 0; i < CharArray.length; i++) {
				WriteData[i] = (byte) CharArray[i];
			}
			//Send the byte array.
			i2C.transaction(WriteData, WriteData.length, null, 0);
		}
	}*/
}