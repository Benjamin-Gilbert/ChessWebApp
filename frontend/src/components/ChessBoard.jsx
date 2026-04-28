function ChessBoard() {
  const board = Array(8).fill(null).map(() => Array(8).fill(null));

  return (
    <div style={{ display: "grid", gridTemplateColumns: "repeat(8, 60px)" }}>
      {board.flat().map((_, i) => (
        <div
          key={i}
          style={{
            width: 60,
            height: 60,
            backgroundColor: (Math.floor(i / 8) + i) % 2 === 0 ? "#eee" : "#444"
          }}
        />
      ))}
    </div>
  );
}

export default ChessBoard;