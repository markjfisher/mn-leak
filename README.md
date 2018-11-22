# micronaut leak example

This repo is to demonstrate a memory leak issue when running a service in
fallback mode by triggering the fallback with a Maybe.error().

Originally reported at https://github.com/micronaut-projects/micronaut-core/issues/952

For JProfiler graphs showing the leak are in the above ticket.

# Setup

Ensure you have a consul instance running

    docker run -p 8500:8500 consul

Run the application:

    ./gradlew :app:run

Run apache bench test with the following:

    $ ab -r -c 350 -n 20000 http://127.0.0.1:8080/v0/product/12345


With multiple runs of the above, increasing response times were observed, and with JProfiler attached, an
increased number of objects created directly correlating to the number of requests performed.

# Testing non-fallback

Comment out the "@Fallback" in StaticProductCatalogueService, and enable it in DefaultProductCatalogueService
so that the Static service is called as the primary service, and rerun the above apache bench tests.

# Results

## With Fallback

Run 1

    Connection Times (ms)
                  min  mean[+/-sd] median   max
    Connect:        0   10  99.2      0    1009
    Processing:     1  686 352.1    687    1754
    Waiting:        1  686 352.1    687    1754
    Total:          1  696 363.1    693    2171

    Percentage of the requests served within a certain time (ms)
      50%    693
      66%    859
      75%    950
      80%    995
      90%   1177
      95%   1312
      98%   1437
      99%   1524
     100%   2171 (longest request)

Run 2

    Connection Times (ms)
                  min  mean[+/-sd] median   max
    Connect:        0    0   0.8      0       8
    Processing:   149 1680 497.3   1673    3324
    Waiting:      149 1680 497.3   1673    3324
    Total:        149 1680 497.2   1674    3325

    Percentage of the requests served within a certain time (ms)
      50%   1674
      66%   1865
      75%   1985
      80%   2069
      90%   2303
      95%   2602
      98%   2839
      99%   2956
     100%   3325 (longest request)

## Without Fallback
    
Run 1

    Connection Times (ms)
                  min  mean[+/-sd] median   max
    Connect:        0    0   0.9      0      10
    Processing:     0  503 286.6    507     968
    Waiting:        0  503 286.6    507     968
    Total:          0  503 286.6    507     973

    Percentage of the requests served within a certain time (ms)
      50%    507
      66%    670
      75%    756
      80%    804
      90%    901
      95%    950
      98%    951
      99%    951
     100%    973 (longest request)

Run 2
    
    Connection Times (ms)
                  min  mean[+/-sd] median   max
    Connect:        0    0   0.7      0       7
    Processing:     0  502 285.7    507     970
    Waiting:        0  502 285.7    507     970
    Total:          0  502 285.7    507     975

    Percentage of the requests served within a certain time (ms)
      50%    507
      66%    663
      75%    752
      80%    803
      90%    897
      95%    950
      98%    951
      99%    951
     100%    975 (longest request)

Run 3

    Connection Times (ms)
                  min  mean[+/-sd] median   max
    Connect:        0    0   0.7      0       7
    Processing:     0  501 286.0    501     968
    Waiting:        0  501 286.0    501     968
    Total:          0  501 286.0    501     972
    
    Percentage of the requests served within a certain time (ms)
      50%    501
      66%    661
      75%    752
      80%    802
      90%    902
      95%    950
      98%    951
      99%    951
     100%    972 (longest request)

