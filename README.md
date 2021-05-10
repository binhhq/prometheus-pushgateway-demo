# prometheus-pushgateway-demo
**Step 1: Run prometheus**
docker run  -p 9090:9090 --name=prometheus -v C:/Users/daisu/Downloads/learnProject/prometheus/config/push-gateway.yml:/etc/prometheus/prometheus.yml --network=binhhq prom/prometheus
	
**Step 2: Run push gateway**

docker run -p 9091:9091 --name=pushgateway --network=binhhq prom/pushgateway

	
**Step 3: Run main class PushPrometheusApplication**
 

**Note:**
- Both container should join same network
- Update file config directory 
