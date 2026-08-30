import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null);

const API_BASE = "http://localhost:8080";

export function AuthProvider({ children }) {


  const [token, setToken] = useState(localStorage.getItem("token"));
  const [username, setUsername] = useState(localStorage.getItem("username"));

  async function register(username, password) {
    const response = await fetch(`${API_BASE}/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Registration failed");
    }

    const data = await response.json();
    saveAuth(data.token, username);
  }

  async function login(username, password) {
    const response = await fetch(`${API_BASE}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json"
       },
      body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Login failed");
    }

    const data = await response.json();
    saveAuth(data.token, username);
  }

  function saveAuth(newToken, newUsername) {
    localStorage.setItem("token", newToken);
    localStorage.setItem("username", newUsername);
    setToken(newToken);
    setUsername(newUsername);
  }

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setToken(null);
    setUsername(null);
  }

  const value = {
    token,
    username,
    isLoggedIn: !!token,
    register,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

// Custom hook so components just call useAuth() instead of importing
// useContext + AuthContext everywhere.
export function useAuth() {
  return useContext(AuthContext);
}