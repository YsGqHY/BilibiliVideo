<h1 align="center">
    BilibiliVideo
</h1>

<hr>

<p align="center" class="shields">
    <a href="https://github.com/BingZi-233/BilibiliVideo/issues" style="text-decoration:none">
        <img src="https://img.shields.io/github/issues/BingZi-233/BilibiliVideo.svg" alt="GitHub issues"/>
    </a>
    <a href="https://github.com/BingZi-233/BilibiliVideo/blob/master/LICENSE" style="text-decoration:none" >
        <img src="https://img.shields.io/github/license/BingZi-233/BilibiliVideo" alt="GitHub license"/>
    </a>
    <a href='https://ci-dev.bingzi.online/job/BilibiliVideo/'>
        <img src='https://ci-dev.bingzi.online/job/BilibiliVideo/badge/icon' alt="Build Status">
    </a>
</p>

## 免责声明

> 1. 本插件仅供学习交流使用，请勿用于商业用途，若有违反国家法律法规以及相关法律法规的行为，本插件概不负责。
> 2. 若因本插件导致B站账号被封禁或视频限流、封禁和下架等情况，本插件概不负责。
> 3. **当您开始使用本插件时，将默认您已经阅读并完全理解上述情况且会遵守相关法律法规和相关公司规定。**

## 命令

- bilibilivideo reload - 重载配置文件（无效命令，仅图个心里安慰）
- bilibilivideo login - 绑定命令
- bilibilivideo show - 查看绑定账户
- bilibilivideo logout - 清理Cookie
- bilibilivideo receive - 领取奖励命令

## 权限

- BilibiliVideo.command.use - 使用命令的权限
- BilibiliVideo.command.reload - 重载配置文件的权限，默认仅OP可用
- BilibiliVideo.command.login - 绑定命令的权限，默认全员可用
- BilibiliVideo.command.show - 查看命令的权限，默认全员可用
- BilibiliVideo.command.logout - 清理Cookie命令的权限，默认全员可用
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

### 🔧构建趋势

![Jenkins](https://ci-dev.bingzi.online/job/BilibiliVideo/buildTimeGraph/png)

## 📉匿名统计

![bStats](https://bstats.org/signatures/bukkit/BilibiliVideo.svg)
