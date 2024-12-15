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

<p align="center" class="shields">
  	<img src="https://img.fastmirror.net/s/2024/10/18/6711d11d4495c.png" alt="" width="auto" style="max-width: 800px">
</p>

## 简介

这是一个让玩家给Bilibili视频一键三连后自助发放奖励的Bukkit插件。流程几乎**全异步操作**并且拥有**强大却很简单**
的配置文件，你可以很轻松的完成许多高级操作。同时，社区十分活跃！你可以在哪里得到疑问解答。当然，本插件的更新十分频繁（某些时间段会变得平稳），内置了更新检查（**完全异步操作
**）用来帮助你进行插件更新告知。

## 链接

- [WIki](https://wiki.bingzi.online/zh/BilibiliVideo/Index)
- [Github](https://github.com/BingZi-233/BilibiliVideo)
- [CI/CD](https://ci-dev.bingzi.online/job/BilibiliVideo)
- [1.X Download](https://ci-dev.bingzi.online/job/BilibiliVideo-1.x/)

### 你需要知道的一些事情

1. `2.0`可能会存在存在部分功能缺失，若无法接受请移步使用 [1.X Download](https://ci-dev.bingzi.online/job/BilibiliVideo-1.x/) 版本。
2. 请在CI/CD或者Github上下载，其他地方（包括群文件）的文件均不受支持。
3. 如果你在使用过程中有任何疑问，都推荐你先看看WIKI。虽然WIKI并没有覆盖插件所有功能，但是已经足够解决大部分问题。
4. 若插件启动时提示有新版本，在 [Github](https://github.com/BingZi-233/BilibiliVideo)
   上并没有看到。请移步到 [CI/CD](https://ci-dev.bingzi.online/job/BilibiliVideo) 上下载，更新检测是准确的。
5. 插件的信息前缀在lang/zh_CN.yml里面修改

## 命令

主命令：/bilibilivideo (别名: /bv, /bilibili-video)

- /bilibilivideo login - 为自己生成B站账号绑定二维码
- /bilibilivideo login <玩家名> - 为指定玩家生成B站账号绑定二维码
- /bilibilivideo logout - 清除自己的B站账号Cookie
- /bilibilivideo logout <玩家名> - 清除指定玩家的B站账号Cookie
- /bilibilivideo receive <BV号> - 领取指定视频的奖励
- /bilibilivideo receive <BV号> <玩家名> - 让指定玩家领取视频奖励
- /bilibilivideo reload - 重载配置文件(仅安慰剂)
- /bilibilivideo unbind - 解除自己的B站账号绑定
- /bilibilivideo unbind <玩家名> - 解除指定玩家的B站账号绑定
- /bilibilivideo url <BV号> - 生成视频二维码
- /bilibilivideo url <BV号> <玩家名> - 向指定玩家发送视频二维码
- /bilibilivideo version - 查看插件版本信息
- /bilibilivideo buvid3 show - 显示当前buvid3
- /bilibilivideo buvid3 refresh - 刷新buvid3

## 权限

基础权限：
- BilibiliVideo.command.use - 使用插件命令的基础权限 (默认: 全员可用)

功能权限：
- BilibiliVideo.command.login - 使用登录绑定命令的权限 (默认: 全员可用)
- BilibiliVideo.command.logout - 清除Cookie的权限 (默认: 全员可用)
- BilibiliVideo.command.logout.player - 清除其他玩家Cookie的权限 (默认: OP可用)
- BilibiliVideo.command.receive - 领取奖励的权限 (默认: 全员可用)
- BilibiliVideo.command.receive.player - 让其他玩家领取奖励的权限 (默认: OP可用)
- BilibiliVideo.command.reload - 重载配置的权限 (默认: OP可用)
- BilibiliVideo.command.unbind - 解除绑定的权限 (默认: OP可用)
- BilibiliVideo.command.url - 生成视频二维码的权限 (默认: 全员可用)
- BilibiliVideo.command.version - 查看版本信息的权限 (默认: OP可用)
- BilibiliVideo.command.buvid3 - 管理buvid3的权限 (默认: OP可用)

## 变量

- %bilibilivideo_bind_mid% - 绑定账户MID，若未绑定显示为"未绑定"
- %bilibilivideo_bind_name% - 绑定账户名称，若未绑定显示为"未绑定"
- %bilibilivideo_bind_time% - 绑定时间，若未绑定显示为"未绑定"
- %bilibilivideo_receive_count% - 已领取的奖励总数
- %bilibilivideo_receive_[bv]% - 检查特定BV视频是否已领取，已领取显示"已领取"，未领取显示"未领取"
    - 例如：%bilibilivideo_receive_BV1Qs411d7pd%

## 配置文件

```yaml
# ▰▰▰▰▰▰▰警告(WARING)▰▰▰▰▰▰▰
# 请仔细阅读下面的提示：
# 1. 如果你只是想执行原版命令，请移步 https://wiki.bingzi.online/zh/BilibiliVideo/config#videoyml 查看详情
# 2. 这里【仅支持】Kether语句，相关语法请移步 https://kether.tabooproject.org/
# 3. 如果%player_name%之类变量无效，请先检查你的PlaceholderAPI是否安装并启用Player拓展。
#    Player拓展下载命令: /papi ecloud download Player
#    下载完成记得重载PlaceholderAPI才有效果，重载命令: /papi reload
#
# 相关链接：
# 1. Kether语句支持文档: https://kether.tabooproject.org/
# 2. WIKI: https://wiki.bingzi.online/zh/BilibiliVideo/config#videoyml
BV1Qs411d7pd:
  # 发送一条ActionBar消息
  - actionbar color "&{#7FB80E}恭喜你领取成功!奖励正在向你飞来!"
  # 暂停一秒
  - sleep 1.0s
  # 干掉玩家
  - command papi 'kill %player_name%' as console
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

<img src="https://ci-dev.bingzi.online/plugin/global-build-stats/showChart?buildStatId=S2SqozN*y32$8Oq$v$l$QxCKc2oDeVLM&time=1707204401291">

> Jenkins由 **[物语云计算](https://www.wuyuidc.com/)** 提供算力支持

## 社区

<img src="https://img.fastmirror.net/s/2023/12/17/657ea2b6ac6be.png" alt="QQ群二维码.png" title="QQ群二维码.png" height="150" width="150" />

## 📉匿名统计

该统计不会采集您的隐私信息，无需担心信息泄漏问题。

<img src="https://bstats.org/signatures/bukkit/BilibiliVideo.svg" alt="Bstats" title="Bstats" width="800" height="400">

## 赞助名单

- [Dummerべ落尘] - 20RMB
- [zhonzia] - 50RMB

## 感谢名单

- [⁶] - 首席体验官

## 开源许可证补充

- **你不能直接将本插件作为付费插件出售，包括但不限于修改、修改后再次发布、二次分发、销售等。**
- **你不能仅修改插件名称、图标、插件描述等信息后声明自己是插件作者。**

## 免责声明

1. **适用性：** 本免责声明适用于所有使用本软件的个人和实体。

2. **限制责任：** 作者对于因使用或无法使用本软件所导致的任何直接、间接、特殊或附带的损失不承担任何责任，包括但不限于利润损失、业务中断、数据丢失或其他经济损失。

3. **软件提供方式：** 本软件及其所有相关内容均按"现状"提供，不提供任何明示或暗示的保证。作者不保证软件的适用性、准确性、完整性或可靠性，也不保证软件不会中断或无错误。

4. **第三方内容：** 本软件可能包含由第三方提供的内容或服务，作者对于这些内容或服务的准确性、完整性、合法性或可靠性不承担任何责任。

5. **权利保留：** 作者保留随时修改、更新或终止软件的权利，恕不另行通知。

6. **使用风险：** 使用者对于使用本软件所产生的一切风险应自行承担。

7. **接受条款：** 使用本软件即代表您同意并接受本免责声明的所有条款和条件。

如有任何疑问或异议，请立即停止使用本软件并与作者联系。

## 鸣谢

[<img src="https://user-images.githubusercontent.com/21148213/121807008-8ffc6700-cc52-11eb-96a7-2f6f260f8fda.png" alt="" width="100">](https://www.jetbrains.com)

特别鸣谢 [JetBrains](https://www.jetbrains.com/) 为本开源项目提供免费的 [Open Source Licenses](https://www.jetbrains.com/opensource/) 。

[<img src="https://camo.githubusercontent.com/a654761ad31039a9c29df9b92b1dc2be62d419f878bf665c3288f90254d58693/68747470733a2f2f77696b692e70746d732e696e6b2f696d616765732f362f36392f5461626f6f6c69622d706e672d626c75652d76322e706e67" alt="" width="300">](https://github.com/TabooLib/taboolib)

特别鸣谢 [TabooLib](https://github.com/TabooLib/taboolib) 为本开源项目提供技术支持。

[<img src="./image/wuyuidc-logo.jpg" alt="" width="200">](https://www.wuyuidc.com/)

特别鸣谢 [物语云计算](https://www.wuyuidc.com/) 为本开源项目提供算力支持。
