# Adaptive Difficulty Shooter Game

A 2D shooter game built using Java and JavaFX that dynamically adjusts its difficulty in real time based on player performance metrics.

---

## Overview

This project implements an adaptive difficulty system that evaluates player performance at fixed time intervals and adjusts enemy behavior accordingly. The game is designed with a layered architecture to separate gameplay mechanics, difficulty evaluation, data handling, and UI rendering.

---

## System Architecture

The system consists of four integrated layers:

### Gameplay Layer
- Player movement and shooting
- Enemy movement and spawning
- Bullet handling
- Hit detection and bypass detection
- Game-over logic

### Adaptive Difficulty Layer
- Collects player performance metrics
- Evaluates difficulty every 5 seconds
- Switches between Easy, Medium, and Hard difficulty levels
- Adjusts enemy speed and spawn rate dynamically

### Data Structures Layer
Used for efficient storage and computation of performance statistics:
- Heap
- Queue
- Sorting
- Binary Search Tree (BST)

### UI Layer
- Renders player, enemies, bullets, and effects
- Displays HUD information such as score, accuracy, bullets fired, bypass count, and difficulty level
- Shows game-over screen

---

## Game Flow

1. Initialize game and start the game loop  
2. Update game frame and collect player metrics  
3. Every 5 seconds:
   - Compute performance statistics
   - Evaluate current difficulty
   - Adjust enemy speed and spawn rate  
4. Continue until a game-over condition is reached  

---

## Technologies Used

- Java 21  
- JavaFX  
- Eclipse IDE  
- Object-Oriented Programming (OOP)  
- MVC-style architecture  
- Game loop architecture  

---
<img width="1058" height="364" alt="image" src="https://github.com/user-attachments/assets/aaabd756-e693-4818-a1e6-596b57201744" />
