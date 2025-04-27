# Галицына Алиса 2 семестр 
Скриншоты в папке screenshots

### promQL

#### RPS
rate(add_user_request_duration_seconds_count[1m])

#### Avg Request Time
increase(add_user_request_duration_seconds_sum[5m]) / increase(add_user_request_duration_seconds_count[5m])


#### Quantile Response
histogram_quantile(0.5, sum by (le) (rate(add_user_request_duration_seconds_bucket[$__rate_interval])))
histogram_quantile(0.75, sum by (le) (rate(add_user_request_duration_seconds_bucket[$__rate_interval])))
histogram_quantile(0.95, sum by (le) (rate(add_user_request_duration_seconds_bucket[$__rate_interval])))
histogram_quantile(0.99, sum by (le) (rate(add_user_request_duration_seconds_bucket[$__rate_interval])))

#### Histogram Response
sum by (le) (rate(add_user_request_duration_seconds_bucket{}[$__rate_interval]))
