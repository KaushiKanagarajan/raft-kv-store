cat > README.md << 'EOF'
# Raft KV Store

A fault-tolerant distributed key-value store built from scratch in Java, using the Raft consensus algorithm.

## Status
- [x] In-memory key-value store
- [ ] HTTP API
- [ ] Write-ahead log (disk persistence)
- [ ] Multi-node cluster
- [ ] Leader election
- [ ] Log replication
- [ ] Fault tolerance tests
- [ ] Benchmarks

## Tech
Java 21, Maven
EOF

git add .
git commit -m "Add README"
git push
