const WebSocket = require('ws');
const http = require('http');

// HTTP Server for Login API
const server = http.createServer((req, res) => {
  // CORS
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

  if (req.url === '/login' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', () => {
      try {
        const { phone, code } = JSON.parse(body);
        if (phone && code === '8888') { // Simple mock validation
          res.writeHead(200, { 'Content-Type': 'application/json' });
          res.end(JSON.stringify({
            success: true,
            token: 'mock-jwt-token-' + Date.now(),
            user: {
              id: 10086,
              name: 'RedBookUser',
              avatar: 'https://picsum.photos/100/100'
            }
          }));
        } else {
          res.writeHead(401, { 'Content-Type': 'application/json' });
          res.end(JSON.stringify({ success: false, message: '验证码错误 (默认8888)' }));
        }
      } catch (e) {
        res.writeHead(400, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ success: false, message: 'Invalid JSON' }));
      }
    });
  } else {
    res.writeHead(404);
    res.end();
  }
});

const wss = new WebSocket.Server({ server });

server.listen(8080, () => {
  console.log('Server started on http://localhost:8080 (WebSocket + HTTP)');
});

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
