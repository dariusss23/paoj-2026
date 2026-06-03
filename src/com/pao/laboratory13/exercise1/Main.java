package com.pao.laboratory13.exercise1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }
        int q = scanner.nextInt();
        scanner.nextLine();
        
        ProtocolEngine engine = new ProtocolEngine();
        int processedCommands = 0;
        
        while (processedCommands < q && scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            
            String result = engine.execute(line);
            System.out.println(result);
            processedCommands++;
        }
        
        scanner.close();
    }
}

class ProtocolEngine {
    private String state = "INIT";
    private int historyCount = 0;

    public String execute(String commandLine) {
        String[] tokens = commandLine.trim().split("\\s+");
        if (tokens.length == 0 || tokens[0].isEmpty()) {
            return "ERR E_PARSE UNKNOWN_COMMAND";
        }
        
        String cmd = tokens[0];
        
        String[] args = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, args, 0, tokens.length - 1);

        switch (cmd) {
            case "AUTH":
                return handleAuth(args);
            case "OPEN":
                return handleOpen(args);
            case "SEND":
                return handleSend(args);
            case "BROADCAST":
                return handleBroadcast(args);
            case "HISTORY":
                return handleHistory(args);
            case "CLOSE":
                return handleClose(args);
            default:
                return "ERR E_PARSE UNKNOWN_COMMAND";
        }
    }

    private String handleAuth(String[] args) {
        if (args.length == 0) {
            return "ERR E_PARSE AUTH";
        }
        if (state.equals("CLOSED")) {
            return "ERR E_STATE CLOSED";
        }
        
        state = "AUTH";
        historyCount = 0;
        return "OK AUTH user=" + args[0];
    }

    private String handleOpen(String[] args) {
        if (args.length > 0) {
            return "ERR E_PARSE OPEN";
        }
        
        if (state.equals("CLOSED")) 
            return "ERR E_STATE CLOSED";
        if (state.equals("OPEN")) 
            return "ERR E_STATE ALREADY_OPEN";
        if (!state.equals("AUTH")) 
            return "ERR E_STATE NOT_OPEN";
        
        state = "OPEN";
        return "OK OPEN";
    }

    private String handleSend(String[] args) {
        if (args.length == 0) {
            return "ERR E_PARSE SEND";
        }
        
        if (state.equals("CLOSED")) 
            return "ERR E_STATE CLOSED";
        if (!state.equals("OPEN")) 
            return "ERR E_STATE NOT_OPEN";
        
        historyCount++;
        return "OK OPEN sent";
    }

    private String handleBroadcast(String[] args) {
        if (args.length == 0) {
            return "ERR E_PARSE BROADCAST";
        }
        
        if (state.equals("CLOSED"))
            return "ERR E_STATE CLOSED";
        if (!state.equals("OPEN"))
            return "ERR E_STATE NOT_OPEN";
        
        historyCount++;
        return "OK OPEN broadcast";
    }

    private String handleHistory(String[] args) {
        if (args.length > 0) {
            return "ERR E_PARSE HISTORY";
        }
        
        if (state.equals("CLOSED"))
            return "ERR E_STATE CLOSED";
        if (!state.equals("OPEN"))
            return "ERR E_STATE NOT_OPEN";
        
        return "OK OPEN history=" + historyCount;
    }

    private String handleClose(String[] args) {
        if (args.length > 0) {
            return "ERR E_PARSE CLOSE";
        }
        
        if (state.equals("CLOSED"))
            return "ERR E_STATE CLOSED";
        if (!state.equals("OPEN"))
            return "ERR E_STATE NOT_OPEN";
        
        state = "CLOSED";
        return "OK CLOSED";
    }
}