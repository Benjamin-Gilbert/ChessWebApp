import React, { useState } from "react";
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


function ChessBoard({ gameID }) {
  const[selectedSquare, setSelectedSquare] = useState(null);
  const[board, setBoard] = useState(initialBoard);
  function sendMove(from, toRow, toCol){
    const moveRequest = {
      gameID: gameID,
      fromRow: from.row,
      fromCol: from.col,
      toRow: toRow,
      toCol: toCol
    };
    console.log("Request: ", moveRequest);
    fetch("http://localhost:8080/game/move", {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(moveRequest)
    })
    .then(res => res.json())
    .then(data => {
      console.log("UPDATED GAME:", data);
      if(data.moveMade === "Move made successfully and added to move history"){
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
  console.log(board);
}
    })
    .catch(err => {
      console.log("Error!", err);
    })
  }
  function handleClick(row, col, piece){
    if(selectedSquare == null){
      if(!piece){return;}
      setSelectedSquare({row, col, piece});
      console.log("Selected:", row, col, piece);
      return;
    }
    console.log("Move:");

    console.log("From:", selectedSquare);
    console.log("To:", { row, col });

    sendMove(selectedSquare, row, col);

    setSelectedSquare(null);
}
  return (
    <div
      style={{
        display: "grid",
        gridTemplateColumns: "repeat(8, 100px)",
        border: "2px solid black"
      }}
    >
      {board.slice().reverse().flat().map((piece, i) => {
        const row = 7 - Math.floor(i / 8);
        const col = i % 8;
        const isLight = (row + col) % 2 === 0;

        return (
          <div
            key={i}
            onClick={() => handleClick(row, col, piece)}
            style={{
              width: 100,
              height: 100,
              backgroundColor: isLight ? "#f0d9b5" : "#b96622",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
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
    
  );
}

export default ChessBoard;