import { useState, useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function ProfileMenu() {
  const [open, setOpen] = useState(false);
  const menuRef = useRef(null);
  const { isLoggedIn, username, logout } = useAuth();
  const navigate = useNavigate();

  // Close the dropdown if the user clicks anywhere outside of it.
  useEffect(() => {
    function handleClickOutside(e) {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  function handleLogout() {
    logout();
    setOpen(false);
    navigate("/");
  }

  return (
    <div style={styles.container} ref={menuRef}>
      <button style={styles.iconButton} onClick={() => setOpen(!open)}>
        {isLoggedIn ? username : "Profile"} <span style={styles.caret}>▾</span>
      </button>

      {open && (
        <div style={styles.dropdown}>
          {isLoggedIn ? (
            <>
              <Link to="/profile" style={styles.dropdownLink} onClick={() => setOpen(false)}>
                View Profile
              </Link>
              <button style={styles.dropdownButton} onClick={handleLogout}>
                Log Out
              </button>
            </>
          ) : (
            <>
              <Link to="/login" style={styles.dropdownLink} onClick={() => setOpen(false)}>
                Log In
              </Link>
              <Link to="/register" style={styles.dropdownLink} onClick={() => setOpen(false)}>
                Sign Up
              </Link>
            </>
          )}
        </div>
      )}
    </div>
  );
}

const styles = {
  container: {
    position: "relative",
    marginLeft: "auto",
    marginRight: "24px",
  },
  iconButton: {
    background: "none",
    border: "none",
    color: "white",
    fontSize: "16px",
    fontWeight: "bold",
    cursor: "pointer",
    display: "flex",
    alignItems: "center",
    gap: "6px",
  },
  caret: {
    fontSize: "12px",
  },
  dropdown: {
    position: "absolute",
    top: "36px",
    right: 0,
    backgroundColor: "#1f1f1f",
    border: "1px solid #333",
    borderRadius: "6px",
    display: "flex",
    flexDirection: "column",
    minWidth: "150px",
    overflow: "hidden",
    zIndex: 100,
  },
  dropdownLink: {
    color: "white",
    textDecoration: "none",
    padding: "10px 16px",
    fontSize: "14px",
  },
  dropdownButton: {
    background: "none",
    border: "none",
    color: "white",
    textAlign: "left",
    padding: "10px 16px",
    fontSize: "14px",
    cursor: "pointer",
  },
};
