cd /home/ubuntu/compose

EXIST_BLUE=$(docker inspect -f '{{.State.Running}}' spring-blue 2>/dev/null)

if [ "$EXIST_BLUE" != "true" ]; then
  docker compose up -d spring-blue
  BEFORE_COLOR="green"
  AFTER_COLOR="blue"
  BEFORE_PORT=8081
  AFTER_PORT=8080
else
  docker compose up -d spring-green
  BEFORE_COLOR="blue"
  AFTER_COLOR="green"
  BEFORE_PORT=8080
  AFTER_PORT=8081
fi

echo "===== ${AFTER_COLOR} server upc(port:${AFTER_PORT}) ====="

for cnt in {1..10}
do
  echo "===== 서버 응답 확인중(${cnt}/10) ====="
  UP=$(curl -s http://localhost:${AFTER_PORT}/health-check)
  if [ "$UP" != "OK" ]; then
    sleep 10
    continue
  else
    break
  fi
done

if [ $cnt -eq 10 ]; then
  echo "===== 서버 실행 실패 ====="
  exit 1
fi