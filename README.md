<h1 align="center">
    BilibiliVideo - 2.0
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

## 链接

- [WIki](https://wiki.bingzi.online/zh/BilibiliVideo/Index)
- [Github](https://github.com/BingZi-233/BilibiliVideo)
- [CI/CD](https://ci-dev.bingzi.online/job/BilibiliVideo)
- [1.X Download](https://ci-dev.bingzi.online/job/BilibiliVideo-1.x/)

### 注意

1. `2.0`可能会存在存在部分功能缺失，若无法接受请移步使用 [1.X Download](https://ci-dev.bingzi.online/job/BilibiliVideo-1.x/) 版本。
2. 请在CI/CD或者Github上下载，其他地方（包括群文件）的文件均不受支持。

### 提示

> 如果你在使用过程中有任何疑问，都推荐你先看看WIKI。虽然WIKI并没有覆盖插件所有功能，但是已经足够解决大部分问题。

### 备注

1. 若插件启动时提示有新版本，在 [Github](https://github.com/BingZi-233/BilibiliVideo)
   上并没有看到。请移步到 [CI/CD](https://ci-dev.bingzi.online/job/BilibiliVideo) 上下载，更新检测是准确的。
2. 插件的信息前缀在lang/zh_CN.yml里面修改

## 命令

> 当您使用 `2.0` 版本的时候，这部分内容可能会有些许变化。

- bilibilivideo reload - 重载配置文件（无效命令，仅图个心里安慰）
- bilibilivideo login [<player_name>]- 绑定命令
- bilibilivideo unbind [player_name] - 解绑命令
- bilibilivideo show - 查看绑定账户
- bilibilivideo logout - 清理Cookie
- bilibilivideo receive [bv] - 领取奖励命令 - 默认为Show模式
- bilibilivideo receive [bv] show - 领取奖励命令(查看模式，并不会使用玩家账户三连视频)
- bilibilivideo receive [bv] auto - 领取奖励命令(自动模式，会使用玩家账户三连视频后根据结果决定是否发放奖励)
- bilibilivideo video [bv] - 生成视频传送门二维码，扫描自动跳转视频页面
- bilibilivideo version - 查看插件版本

## 权限

> 当您使用 `2.0` 版本的时候，这部分内容可能会有些许变化。

- BilibiliVideo.command.use - 使用命令的权限
- BilibiliVideo.command.reload - 重载配置文件的权限，默认仅OP可用
- BilibiliVideo.command.login - 绑定命令的权限，默认全员可用
- BilibiliVideo.command.login.other - 绑定命令的权限，需要额外分配（不建议进行分配，该权限可为别人绑定账户）
- BilibiliVideo.command.unbind - 绑定命令的权限，默认仅OP可用
- BilibiliVideo.command.show - 查看命令的权限，默认全员可用
- BilibiliVideo.command.logout - 清理Cookie命令的权限，默认全员可用
- BilibiliVideo.command.receive - 领取奖励命令的权限，默认全员可用
- BilibiliVideo.command.version - 查看插件版本命令的权限，默认仅OP可用

## 变量

> 当您使用 `2.0` 版本的时候，这部分内容可能会有些许变化。

- %BilibiliVideo_uid% - 绑定账户UID，若为绑定显示为 N/A
    - 1.4.4之后可用
- %BilibiliVideo_uname% - 绑定账户名称，若为绑定显示为 N/A
    - 1.4.4之后可用
- %BilibiliVideo_check_[bv]% - 是否领取对应BV视频奖励，若为未领取显示为 false，领取显示为 true
    - 1.6.2之后可用

## 配置文件

> 当您使用 `2.0` 版本的时候，这部分内容可能会有些许变化。

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
