import express from 'express'
import {Builder, Nuxt} from 'nuxt'

const app = express();
const host = process.env.HOST || '0.0.0.0';
const port = process.env.PORT || 8080;

app.set('port', port);
app.get('/health', (req, res) => {
  res.status(200).set("Connection", "close").send({hello: 'Hello, I am healthy!'}).end();
});
app.get('/liveness_check', (req, res) => {
  res.status(200).set("Connection", "close").send({hello: 'Hello, I am alive!'}).end();
});
app.get('/readiness_check', (req, res) => {
  res.status(200).set("Connection", "close").send({hello: 'Hello, I am ready!'}).end();
});

let config = require('./nuxt.config.js');
config.dev = !(process.env.NODE_ENV === 'production');
const nuxt = new Nuxt(config);
if (config.dev) {
  const builder = new Builder(nuxt);
  builder.build();
}

app.use(nuxt.render);
app.listen(port, host);
console.log('Server listening on ' + host + ':' + port); // eslint-disable-line no-console
