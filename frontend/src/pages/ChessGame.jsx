import ChessBoard from "../components/ChessBoard";

export default function ChessGame() {
  return (
    <div style={styles.page}>
      
      {/* TOP BAR */}
      

      {/* MAIN AREA */}
      <div style={styles.main}>
        
        {/*Chat and game functions.*/}
        <div style={styles.chatPanel}>
          <h3>Chat</h3>
          <div style={styles.chatBox}>
            <div>Player A (2052) vs Player B (1500)</div>
            <div>+2 win/-6 draw/-12 loss</div>
          </div>
          <h3>Game</h3>
          <button>Resign</button>
          <button>Offer Draw</button>
        </div>

        {/* CENTER: BOARD */}
        <div style={styles.boardArea}>
          <div style={styles.topBar}>
            <div>    Player B (1500)    </div>
          </div>
          <ChessBoard />
          <div style = {styles.bottomBar}>
            <div>    Player A (2052)    </div>
          </div>
        </div>

        {/*moves list and openings maybe?*/}
        <div style={styles.movesPanel}>
          <div style={{ marginBottom: 10, textAlign: "center", fontSize: "18px"}}>Moves</div>
          <table style = {styles.table}>
            <tr style = {{backgroundColor: "#131212"}}>
              <td>1</td>
              <td>e4</td>
              <td>e5</td>
            </tr>
            <tr>
              <td>2</td>
              <td>Nf3</td>
              <td>Nc6</td>
            </tr>
            <tr style = {{backgroundColor: "#131212"}}>
              <td>3</td>
              <td>Bc4</td>
              <td>Nf6</td>
            </tr>
          </table>
          </div>
        </div>

      </div>
  );
}

const styles = {
  page: {
    height: "100vh",
    display: "flex",
    flexDirection: "column",
    backgroundColor: "#131212",
    color: "white",
  },

  topBar: {
    height: "60px",
    width: "800px",
    display: "flex",
    flexDirection: "column",
    justifyContent: "left",
    alignItems: "left",
    backgroundColor: "#1f1f1f",
    borderBottom: "3px solid #444",
    marginBottom: "4px",
  },
  bottomBar: {
    height: "60px",
    width: "800px",
    display: "flex",
    flexDirection: "column",
    justifyContent: "left",
    alignItems: "left",    
    backgroundColor: "#1f1f1f",
    borderBottom: "3px solid #444",
    marginTop: "4px",
  },

  main: {
    flex: 1,
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    gap: "20px",
  },

  boardArea: {
    display: "flex",
    justifyContent: "left",
    alignItems: "left",
    flexDirection: "column",
  },

  movesPanel: {
    width: "200px",
    height: "500px",
    backgroundColor: "#1f1f1f",
    padding: "10px",
    borderRadius: "8px",
  },
  chatPanel: {
    width: "200px",
    height: "500px",
    backgroundColor: "#1f1f1f",
    padding: "10px",
    borderRadius: "8px",
  },
  chatBox: {
    fontSize: "14px",
    height: "300px",
  },

  table: {
    width: "100%",
    padding: "10px",
    fontSize: "14px",
    borderCollapse: "collapse",
  }
};