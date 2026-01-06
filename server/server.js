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
  ws.send(JSON.stringify({ type: 'welcome', text: '系统通知：欢迎连接到 RedBook 实时服务' }));

  // Simulate periodic notifications
  const interval = setInterval(() => {
    const candidates = [
      JSON.stringify({ type: 'like', postId: '8888', text: '有人点赞了你' }),
      JSON.stringify({ type: 'comment', text: '收到一条新评论' }),
      JSON.stringify({ type: 'follow', text: '有人关注了你' }),
      JSON.stringify({ type: 'collect', text: '你的笔记被收藏了' }),
      JSON.stringify({ type: 'promo', text: '热门活动推荐：周末去哪儿' }),
    ];
    const msg = candidates[Math.floor(Math.random() * candidates.length)];
    ws.send(msg);
  }, 20000);

  ws.on('close', () => {
    console.log('Client disconnected');
    clearInterval(interval);
  });
});
