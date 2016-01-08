package com.server;

import com.backend.Airline;
import com.backend.Airport;
import com.backend.Game;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/AirlineServerEndpoint")
public class AirlineServerEndpoint {

	// Create Sessions Array
	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());;

	public static Game game;

	// Create Session
	@OnOpen
	public void open(Session session) {
		sessions.add(session);
	}

	@OnClose
	public void close(Session session) {
		sessions.remove(session);
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		splitMessage(message);

		String airlineName = (String) session.getUserProperties().get("airlineName");
		if (airlineName == null) {
			session.getUserProperties().put("airlineName", message);
		}

	}

	private void sendToAllConnectedSessions(JsonObject message) {
		for (Session session : sessions) {
			sendToSession(session, message);
		}
	}

	private void sendToSession(Session session, JsonObject message) {
		try {
			session.getBasicRemote().sendText(message.toString());
		} catch (IOException ex) {
		}
	}

	private void splitMessage(String message) {
		String[] request = message.split("�");
		
		switch (request[0]) {
		case "create":
			if (game == null) {

				System.out.println("Game does not exist");
				game = new Game(this);
			}

			game.addPlayer(request[1]);
			System.out.println(game.getPlayerCount());
			break;
		case "buyPlane":
			//Messagetype of (Airline,Planetype, Planename, Planelocationairport)
			Airline airlineBuy = game.getAirlineByName(request[1]);
			String planeType = request[2];
			String planeNameBuy = request[3];
			Airport location = game.getAirportByName(request[4]);
			airlineBuy.buyPlane(planeType, planeNameBuy, location);
//			game.getAirlineByName(request[1]).buyPlane(request[2],request[3]);
			break;
		case "occupyRoute":
			Airline airlineOccupy = game.getAirlineByName(request[1]);
			String planeNameOccupy = request[2];
			String routeName = request[3];
			game.occupyRoute(game.getRouteByName(routeName), airlineOccupy.getPlaneByName(planeNameOccupy));;
			break;
		case "takeCredit":
			Airline a = game.getAirlineByName(request[1]);
			if(request[2].equals("1"))a.takeCreditType1(Integer.parseInt(request[3]));
			if(request[2].equals("2"))a.takeCreditType2(Integer.parseInt(request[3]));
			if(request[2].equals("3"))a.takeCreditType3(Integer.parseInt(request[3]));
			break;
		case "buySocialMediaSoftware":
			game.getAirlineByName(request[1]).getITDept().buySocialMediaSoftware(game.getAirlineByName(request[1]));
			break;
		case "buyHRModule":
			game.getAirlineByName(request[1]).getITDept().buyHRModule(game.getAirlineByName(request[1]));
			break;
		case "buyAccountingModule":
			game.getAirlineByName(request[1]).getITDept().buyAccountingModule(game.getAirlineByName(request[1]));
			break;
		case "sellSocialMediaSoftware":
			game.getAirlineByName(request[1]).getITDept().sellSocialMediaSoftware(game.getAirlineByName(request[1]));
			break;
		case "sellHRModule":
			game.getAirlineByName(request[1]).getITDept().sellHRModule(game.getAirlineByName(request[1]));
			break;
		case "sellAccountingModule":
			game.getAirlineByName(request[1]).getITDept().sellAccountingModule(game.getAirlineByName(request[1]));
			break;
		default:
			break;
		}
	}

	public void sendJSONToAll(String s) {
		JsonObject json = Json.createReader(new StringReader(s)).readObject();
		sendToAllConnectedSessions(json);
	}
}
