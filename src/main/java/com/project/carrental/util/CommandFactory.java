package com.project.carrental.util;

import com.project.carrental.commands.AdminZoneButtonCommand;
import com.project.carrental.commands.CalculateCostCommand;
import com.project.carrental.commands.ConfirmOrderCommand;
import com.project.carrental.commands.ConfirmPaymentCommand;
import com.project.carrental.commands.CreateOrderCommand;
import com.project.carrental.commands.GiveVehicleCommand;
import com.project.carrental.commands.HomeButtonCommand;
import com.project.carrental.commands.ICommand;
import com.project.carrental.commands.LoadOrderListCommand;
import com.project.carrental.commands.LogInCommand;
import com.project.carrental.commands.LogOutCommand;
import com.project.carrental.commands.MakeOrderButtonCommand;
import com.project.carrental.commands.NoCommand;
import com.project.carrental.commands.RegisterCommand;
import com.project.carrental.commands.RejectOrderCommand;
import com.project.carrental.commands.ResetOrderCommand;
import com.project.carrental.commands.ReturnDamagedVehicleCommand;
import com.project.carrental.commands.ReturnVehicleCommand;
import com.project.carrental.commands.SelectOrderCommand;
import com.project.carrental.services.VehicleService;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * Class is a factory method that produces an instance of the proper command
 *
 */
public class CommandFactory {


    private static CommandFactory instance;
    HashMap<String, ICommand> commands = new HashMap<>();

    private CommandFactory() {
        //filling the map with available commands
        commands.put("login", new LogInCommand());
        commands.put("logout", new LogOutCommand());
        commands.put("homeButton", new HomeButtonCommand());
        commands.put("registration", new RegisterCommand());

        commands.put("makeOrderButton", new MakeOrderButtonCommand());
        commands.put("adminZoneButton", new AdminZoneButtonCommand());

        commands.put("calculateCost", new CalculateCostCommand(new VehicleService()));
        commands.put("createOrder", new CreateOrderCommand());

        commands.put("loadOrderList", new LoadOrderListCommand());
        commands.put("selectOrder", new SelectOrderCommand());

        commands.put("confirmOrder", new ConfirmOrderCommand());
        commands.put("rejectOrder", new RejectOrderCommand());
        commands.put("giveVehicle", new GiveVehicleCommand());
        commands.put("returnVehicle", new ReturnVehicleCommand());
        commands.put("returnDamagedVehicle", new ReturnDamagedVehicleCommand());
        commands.put("confirmPayment", new ConfirmPaymentCommand());
        commands.put("resetOrder", new ResetOrderCommand());
    }

    public static synchronized CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public ICommand getCommand(HttpServletRequest req) {
        String action = req.getParameter("command");
        ICommand command = commands.get(action);
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}