## 命令

- bilibilivideo reload - 重载配置文件（无效命令，仅图个心里安慰）
- bilibilivideo login - 绑定命令
- bilibilivideo receive - 领取奖励命令

## 权限

- BilibiliVideo.command.use - 使用命令的权限
- BilibiliVideo.command.reload - 重载配置文件的权限，默认仅OP可用
- BilibiliVideo.command.login - 绑定命令的权限，默认全员可用
- BilibiliVideo.command.receive - 领取奖励命令的权限，默认全员可用

## 配置文件

```yaml
# 命令支持Kether
# 例如: tell "hello world"
# 相关文档：https://kether.tabooproject.org/
BV1Qs411d7pd:
  - "tell 'hello world'"
```

> 如果需要增加更多，按照上述样式复制即可。

以下是例子

```yaml
BV1Qs411d7pd:
  - "tell 'hello world'"
  - "tell 'hello world'"
BV1Qs411d7pD:
  - "tell 'hello world'"
  - "tell 'hello world'"
  - "tell 'hello world'"
  - "tell 'hello world'"
```

## 🎉CI/CD

- [GitHub Action](https://github.com/BingZi-233/BilibiliVideo/actions)(推荐国外访问)
- [Jenkins](https://ci-dev.bingzi.online/job/BilibiliVideo/)(推荐大陆访问)

> Jenkins由 **[物语云计算](https://www.wuyuidc.com/)** 提供算力支持