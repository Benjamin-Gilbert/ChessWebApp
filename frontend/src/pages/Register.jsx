import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const { register } = useAuth();
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);

    if (password !== confirmPassword) {
      setError("Passwords don't match.");
      return;
    }
    if (password.length < 8) {
      setError("Password must be at least 8 characters.");
      return;
    }

    setSubmitting(true);
    try {
      await register(username, password);
      navigate("/");
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div style={styles.page}>
      <form style={styles.card} onSubmit={handleSubmit}>
        <h1 style={styles.heading}>Create an account</h1>

        <input
          style={styles.input}
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <input
          style={styles.input}
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <input
          style={styles.input}
          type="password"
          placeholder="Confirm password"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          required
        />

        {error && <p style={styles.error}>{error}</p>}

        <button style={styles.button} type="submit" disabled={submitting}>
          {submitting ? "Creating account..." : "Sign Up"}
        </button>

        <p style={styles.footerText}>
          Already have an account? <Link to="/login" style={styles.link}>Log in</Link>
        </p>
      </form>
    </div>
  );
}

const styles = {
  page: {
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#131212",
    color: "white",
  },
  card: {
    display: "flex",
    flexDirection: "column",
    gap: "16px",
    width: "340px",
    backgroundColor: "#1f1f1f",
    padding: "32px",
    borderRadius: "10px",
  },
  heading: {
    margin: "0 0 8px 0",
    textAlign: "center",
  },
  input: {
    height: "44px",
    backgroundColor: "#222",
    color: "white",
    border: "1px solid #333",
    borderRadius: "4px",
    padding: "0 12px",
    fontSize: "15px",
  },
  button: {
    height: "46px",
    backgroundColor: "#3a7d44",
    color: "white",
    border: "none",
    borderRadius: "4px",
    fontSize: "16px",
    cursor: "pointer",
  },
  error: {
    color: "#ff6b6b",
    fontSize: "14px",
    margin: 0,
  },
  footerText: {
    textAlign: "center",
    fontSize: "14px",
    color: "#aaa",
    margin: 0,
  },
  link: {
    color: "#7fbfff",
    textDecoration: "none",
  },
};