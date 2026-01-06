const WebSocket = require('ws');

const wss = new WebSocket.Server({ port: 8080 });

console.log('WebSocket server started on ws://localhost:8080');

wss.on('connection', function connection(ws) {
  console.log('Client connected');

  ws.on('message', function incoming(message) {
    console.log('received: %s', message);
    
    // Echo back (optional)
    // ws.send(`Server received: ${message}`);
  });

  // Send a welcome message
  ws.send('系统通知：欢迎连接到 RedBook 实时服务');

  // Simulate periodic notifications
  const interval = setInterval(() => {
    const messages = [
      "收到一条新点赞",
      "有人关注了你",
      "你的笔记被收藏了",
      "收到一条新评论",
      "热门活动推荐：周末去哪儿"
    ];
    const randomMsg = messages[Math.floor(Math.random() * messages.length)];
    ws.send(randomMsg);
  }, 5000);

  ws.on('close', () => {
    console.log('Client disconnected');
    clearInterval(interval);
  });
});
