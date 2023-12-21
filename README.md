<h1 align="center">
    BilibiliVideo
</h1>

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

## WIKI

> 如果你在使用过程中有任何疑问，都推荐你先看看WIKI。虽然WIKI并没有覆盖插件所有功能，但是已经足够解决大部分问题。

[地址](https://www.yuque.com/sakuraziyou/bilibili_video)

## 命令

- bilibilivideo reload - 重载配置文件（无效命令，仅图个心里安慰）
- bilibilivideo login - 绑定命令
- bilibilivideo unbind [player_name] - 解绑命令
- bilibilivideo show - 查看绑定账户
- bilibilivideo logout - 清理Cookie
- bilibilivideo receive [bv] - 领取奖励命令
- bilibilivideo receive [bv] show - 领取奖励命令(查看模式，并不会使用玩家账户三连视频)

## 权限

- BilibiliVideo.command.use - 使用命令的权限
- BilibiliVideo.command.reload - 重载配置文件的权限，默认仅OP可用
- BilibiliVideo.command.login - 绑定命令的权限，默认全员可用
- BilibiliVideo.command.unbind - 绑定命令的权限，默认仅OP可用
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

## 社区

<img src="https://img.fastmirror.net/s/2023/12/17/657ea2b6ac6be.png" alt="QQ群二维码.png" title="QQ群二维码.png" height="150" width="150" />

## 📉匿名统计

该统计不会采集您的隐私信息，无需担心信息泄漏问题。

<img src="https://bstats.org/signatures/bukkit/BilibiliVideo.svg" alt="Bstats" title="Bstats" width="800" height="400">

## 免责声明

1. **适用性：** 本免责声明适用于所有使用本软件的个人和实体。

2. **限制责任：** 作者对于因使用或无法使用本软件所导致的任何直接、间接、特殊或附带的损失不承担任何责任，包括但不限于利润损失、业务中断、数据丢失或其他经济损失。

3. **软件提供方式：** 本软件及其所有相关内容均按"现状"提供，不提供任何明示或暗示的保证。作者不保证软件的适用性、准确性、完整性或可靠性，也不保证软件不会中断或无错误。

4. **第三方内容：** 本软件可能包含由第三方提供的内容或服务，作者对于这些内容或服务的准确性、完整性、合法性或可靠性不承担任何责任。

5. **权利保留：** 作者保留随时修改、更新或终止软件的权利，恕不另行通知。

6. **使用风险：** 使用者对于使用本软件所产生的一切风险应自行承担。

7. **接受条款：** 使用本软件即代表您同意并接受本免责声明的所有条款和条件。

如有任何疑问或异议，请立即停止使用本软件并与作者联系。
