[#if sysContent!="0"]
[@cms.SysContent style=sysContent/]
[#elseif userContent!=""]
[@cms.UserContent style=userContent/]
[#else]
��ѡ��������ʽ
[/#if]
[#if isPage=="1" && pagination.list?size > 0]
	[#if sysPage!="0"]
		[@cms.SysPage style=sysPage cssClass=pageClass cssStyle=pageStyle/]
	[#elseif userPage!=""]
		[@cms.UserPage name=userPage cssClass=pageClass cssStyle=pageStyle solution=upSolution webRes=upWebRes/]
	[/#if]
[/#if]