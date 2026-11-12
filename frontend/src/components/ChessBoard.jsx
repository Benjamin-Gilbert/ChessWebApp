import React, { useState, useEffect, useRef } from "react";
import wp from "../assets/pieces/wp.svg";
import wr from "../assets/pieces/wr.svg";
import wn from "../assets/pieces/wn.svg";
import wb from "../assets/pieces/wb.svg";
import wq from "../assets/pieces/wq.svg";
import wk from "../assets/pieces/wk.svg";

import bp from "../assets/pieces/bp.svg";
import br from "../assets/pieces/br.svg";
import bn from "../assets/pieces/bn.svg";
import bb from "../assets/pieces/bb.svg";
import bq from "../assets/pieces/bq.svg";
import bk from "../assets/pieces/bk.svg";
import { useAuth } from "../context/AuthContext";

const pieces = {
  wp, wr, wn, wb, wq, wk,
  bp, br, bn, bb, bq, bk
};


const initialBoard = [
  ["wr","wn","wb","wq","wk","wb","wn","wr"],
  ["wp","wp","wp","wp","wp","wp","wp","wp"],
  [null,null,null,null,null,null,null,null],
  [null,null,null,null,null,null,null,null],
  [null,null,null,null,null,null,null,null],
  [null,null,null,null,null,null,null,null],
  ["bp","bp","bp","bp","bp","bp","bp","bp"],
  ["br","bn","bb","bq","bk","bb","bn","br"]
];
const files = ["a", "b", "c", "d", "e", "f", "g", "h"];


function ChessBoard({ gameID, onGameUpdate, gameOver, turn, playerColor, boardFlipped}) {

const {token} = useAuth();

  const[selectedSquare, setSelectedSquare] = useState(null);
  const[board, setBoard] = useState(initialBoard);
  const[lastMove, setLastMove] = useState(null);
  const[legalMoves, setLegalMoves] = useState([]);
  const[promotionMove, setPromotionMove] = useState(null);
  const[whiteClock, setWhiteClock] = useState(300000);
  const[blackClock, setBlackClock] = useState(300000);
  function choosePromotion(choice){

    sendMove(
        promotionMove.from,
        promotionMove.toRow,
        promotionMove.toCol,
        choice
    );

    setPromotionMove(null);

}
function formatTime(ms) {
  if (ms == null) return "--:--";
  const totalSeconds = Math.max(0, Math.floor(ms / 1000));
  const minutes = Math.floor(totalSeconds / 60);
  const seconds = totalSeconds % 60;
  return `${minutes}:${seconds.toString().padStart(2, "0")}`;
}
const topColor = boardFlipped ? "white" : "black";
const bottomColor = boardFlipped ? "black" : "white";
const topTime = topColor === "white" ? whiteClock : blackClock;
const bottomTime = bottomColor === "white" ? whiteClock : blackClock;
  function shouldPromote(from, toRow) {

    if(!from.piece) return false;

    if(from.piece[1] !== "p")
        return false;

    return (
        (from.piece[0] === "w" && toRow === 7) ||
        (from.piece[0] === "b" && toRow === 0)
    );
}
useEffect(() => {
  const interval = setInterval(() => {
    if(turn === "white"){setWhiteClock(t => Math.max(0, t - 1000));}
    else if(turn === "black"){setBlackClock(t => Math.max(0, t - 1000));}
  }, 1000);
  return () => clearInterval(interval);
}, [turn]);
useEffect(() => {
  if (!gameID || !token) return;

  function fetchBoardState() {
    fetch(`http://localhost:8080/game/${gameID}`, {
      headers: { "Authorization": `Bearer ${token}` },
    })
      .then(res => res.json())
      .then(data => {
        if (data.mirrorBoard) {
          const newBoard = [];
          for (let row = 0; row < 8; row++) {
            const newRow = [];
            for (let col = 0; col < 8; col++) {
              newRow.push(data.mirrorBoard[row][col]?.frontendKey ?? null);
            }
            newBoard.push(newRow);
          }
          setBoard(newBoard);
          setWhiteClock(data.whiteClock);
          setBlackClock(data.blackClock);
          onGameUpdate(data); 
        }
      })
      .catch(err => console.error("Board poll error:", err));
  }

  const interval = setInterval(fetchBoardState, 1500); // check every 1.5s
  return () => clearInterval(interval);
}, [gameID, token]);
  function sendMove(from, toRow, toCol, promotion){
    const moveRequest = {
      gameID: gameID,
      fromRow: from.row,
      fromCol: from.col,
      toRow: toRow,
      toCol: toCol,
      promotion: promotion
    };
    console.log("Request: ", moveRequest);
    fetch("http://localhost:8080/game/move", {
      method: "POST",
      headers: {"Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
      body: JSON.stringify(moveRequest)
    })
    .then(res => res.json())
    .then(data => {
      console.log("UPDATED GAME:", data);
      if(data.moveMade === "Move made successfully and added to move history" && (data.whiteClock !== 0 || data.blackClock !== 0)){
        const newBoard = [];

  for (let row = 0; row < 8; row++) {

    const newRow = [];

    for (let col = 0; col < 8; col++) {
      if(data.mirrorBoard[row][col] != null){
      newRow.push(data.mirrorBoard[row][col].frontendKey);
    }
    else{newRow.push(null);}
  }

    newBoard.push(newRow);
  }

  setBoard(newBoard);
  setWhiteClock(data.whiteClock);
  setBlackClock(data.blackClock);
  setLegalMoves([]);
  setLastMove({
    from,
    to: {
      row: toRow, 
      col: toCol
    }
    //TODO: handle time out logic.
  });
  console.log("Last Move ", lastMove);

  console.log(data.turn);
  onGameUpdate(data);
}
    })
    .catch(err => {
      console.log("Error!", err);
    })
  }
  function handleClick(row, col, piece){
    if(gameOver == true){
      return;
    }
    if(!turn || turn !== playerColor){
      return;
    }

    if(selectedSquare == null){
      if(!piece){return;}
      if(playerColor[0] !== piece[0]){return;}
      setSelectedSquare({row, col, piece});
      console.log("Selected:", row, col, piece);
      fetch(`http://localhost:8080/game/generateMoves`, {
        method: "POST",
        headers: {"Content-Type":"application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify({
            gameID: gameID,
            toRow: row,
            toCol: col
        })
    })
    .then(res => res.json())
    .then(data => {
      console.log(data);
        setLegalMoves(data.legalMoves);
        console.log("Legal moves: ", legalMoves);
    });

      return;
    }
    if(
      piece &&
      piece[0] === selectedSquare.piece[0]
  ){
      setSelectedSquare({
          row,
          col,
          piece
      });
      setLegalMoves([]);
      fetch(`http://localhost:8080/game/generateMoves`, {
        method: "POST",
        headers: {"Content-Type":"application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify({
            gameID: gameID,
            toRow: row,
            toCol: col
        })
    })
    .then(res => res.json())
    .then(data => {
        setLegalMoves(data.legalMoves);
    });

      return;
  }
   if(shouldPromote(selectedSquare, row)){
      setPromotionMove({
        from: selectedSquare,
        toRow: row,
        toCol: col
      });
      return;
    }
    console.log("Move:");

    console.log("From:", selectedSquare);
    console.log("To:", { row, col });

    sendMove(selectedSquare, row, col, null);

    setSelectedSquare(null);
}
  return (
    <div style={{position: "relative"}}>
      <div style={{...styles.clock,
      opacity: turn === topColor ? 1 : 0.5,
      fontWeight: turn === topColor ? "bold" : "normal",}}>{formatTime(topTime)}</div>
    <div style={{ display: "grid", gridTemplateColumns: "repeat(8, 100px)", border: "2px solid black" }}>
      {Array.from({ length: 64 }, (_, i) => {
        // This is the ONLY part that changes based on orientation:
        const displayRow = boardFlipped ? Math.floor(i / 8) : 7 - Math.floor(i / 8);
        const displayCol = boardFlipped ? 7 - (i % 8) : i % 8;

        // These stay exactly what they always were — the real board coordinates.
        const row = displayRow;
        const col = displayCol;
        const piece = board[row][col];

        const isLight = (row + col) % 2 === 0;
        const isLastMove = lastMove &&
          ((lastMove.from.row === row && lastMove.from.col === col) ||
          (lastMove.to.row === row && lastMove.to.col === col));
        const isSelected = selectedSquare && selectedSquare.row === row && selectedSquare.col === col;
        const isLegalMove = legalMoves.some(([r, c]) => r === row && c === col);

        return (
          <div
            key={i}
            onClick={() => handleClick(row, col, piece)}
            style={{
              width: 100,
              height: 100,
              position: "relative",
              backgroundColor: isSelected ? "#c9d652" : isLastMove ? "#c9d652" : isLight ? "#b96622" : "#f0d9b5",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            {col === 0 && (

<div
style={{
position:"absolute",

top:4,

left:6,

fontSize:20,

fontWeight:"bold",

color:
isLight
? "#f0d9b5"
: "#b96622"
}}
>

{row + 1}

</div>

)}
{row === 0 && (

<div
style={{
position:"absolute",

bottom:4,

right:6,

fontSize:20,

fontWeight:"bold",

color:
isLight
? "#f0d9b5"
: "#b96622"
}}
>

{files[col]}

</div>

)}

{isLegalMove && !piece && (
  <div
    style={{
      width: "25px",
      height: "25px",
      backgroundColor: "rgba(0,0,0,0.35)",
      borderRadius: "50%",
      position: "absolute",
      zIndex: 1
    }}
  />
)}
{isLegalMove && piece && (
  <div
    style={{
      width: "85px",
      height: "85px",
      border: "8px solid rgba(0,0,0,0.35)",
      borderRadius: "50%",
      position: "absolute",
      zIndex: 1
    }}
  />
)}
            {piece && (
              <img
                src={pieces[piece]}
                alt={piece}
                style={{ width: "95%", height: "95%" }}
              />
            )}
          </div>
        );
      })}
    </div>
    <div style={{...styles.clock, 
      opacity: turn === bottomColor ? 1 : 0.5,
      fontWeight: turn === bottomColor ? "bold" : "normal",
    }}>{formatTime(bottomTime)}</div>
    {promotionMove && (

<div
style={{
position: "absolute",

left: `${promotionMove.toCol * 100}px`,

bottom:
promotionMove.from.piece[0] === "w"
? `${promotionMove.toRow * 100}px`
: `${(7 - promotionMove.toRow) * 100}px`,

display: "flex",

flexDirection:
promotionMove.from.piece[0] === "w"
? "column-reverse"
: "column",

backgroundColor: "#262421",

borderRadius: "6px",

zIndex: 1000,

overflow: "hidden",
height: "200px",
width: "100px"
}}
>

<button onClick={() => choosePromotion("queen")} style={{height: "50px", fontSize: "40px"}}>♛</button>

<button onClick={() => choosePromotion("rook")} style={{height: "50px", fontSize: "40px"}}>♜</button>

<button onClick={() => choosePromotion("bishop")} style={{height: "50px", fontSize: "40px"}}>♝</button>

<button onClick={() => choosePromotion("knight")} style={{height: "50px", fontSize: "40px"}}>♞</button>

</div>

)}
</div>
  );
  
}  
const styles = {
  clock: {
    width: "800px",
    padding: "8px 16px",
    fontSize: "28px",
    fontFamily: "monospace",
    color: "white",
    backgroundColor: "#1f1f1f",
    textAlign: "right",
    boxSizing: "border-box",
  },
};      

export default ChessBoard;