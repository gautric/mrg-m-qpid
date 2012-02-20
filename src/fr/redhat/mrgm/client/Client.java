package fr.redhat.mrgm.client;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {

	public static void main(String[] args) {
		Client producer = new Client();
		producer.runTest();
	}

	private void runTest() {
		try {
			Context context = new InitialContext();

			ConnectionFactory connectionFactory = (ConnectionFactory) context
					.lookup("qpidConnectionfactory");
			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = (Destination) context
					.lookup("MyQueue");

			MessageProducer messageProducer = session
					.createProducer(destination);
			MessageConsumer messageConsumer = session
					.createConsumer(destination);

			TextMessage message = session.createTextMessage("Hello world!");
			messageProducer.send(message);

			message = (TextMessage) messageConsumer.receive();
			System.out.println(message.getText());

			connection.close();
			context.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
