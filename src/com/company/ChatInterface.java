package com.company;

import javax.annotation.processing.Completion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ai.openai.api.models.Completion;
import ai.openai.api.services.CompletionService;

public class ChatInterface extends JFrame implements ActionListener {

    private JTextField textField;
    private JTextArea textArea;
    private JButton sendButton;
    private CompletionService completionService;

    private final String MODEL_ID = "";

    public void ChatGPTApplication() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SLASSCOM CHAT Application");

        // Create text field for entering messages
        textField = new JTextField(30);
        textField.addActionListener(this);

        // Create text area to display messages
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Create button to send messages
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        // Create panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.SOUTH);

        // Add panel to frame
        setContentPane(panel);
        pack();
        setVisible(true);

        // Set up the completion service for ChatGPT
        completionService = new CompletionService("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") {
            @Override
            public Future submit(Callable task) {
                return null;
            }

            @Override
            public Future submit(Runnable task, Object result) {
                return null;
            }

            @Override
            public Future take() throws InterruptedException {
                return null;
            }

            @Override
            public Future poll() {
                return null;
            }

            @Override
            public Future poll(long timeout, TimeUnit unit) throws InterruptedException {
                return null;
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton || e.getSource() == textField) {
            String message = textField.getText();
            if (!message.isEmpty()) {
                textArea.append("Client : " + message + "\n");

                // Send the message to ChatGPT API and get the response
                try {
                    Completion completion = completionService.complete(MODEL_ID, message).get();
                    String response = completion.getChoices().get(0).getText();
                    textArea.append("ChatGPT Response : " + response + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                textField.setText("");
            }
        }
    }
}
