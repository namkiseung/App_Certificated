package com.example.protected_root;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    String uid;
    String message;
    public Map<String, Boolean> users = new HashMap<>();
    public Map<String, Comment> comments = new HashMap<>();

    public static class Comment{
        String uid;
        String message;
    }
}
