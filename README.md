DVD PRIME Mobile Server
============

![Logo](gitsite/static/dp-logo.png)

[dvdprime.com][1] 접속 APP을 지원하기 위한 모바일 서버입니다.

Use Libraries
--------

1. Jersey Framework
기본 RESTful API를 제공하기 위한 프레임워크로 [Jersey][2]를 사용하였습니다.
제공하는 API는 설명하지 않겠습니다. 

2. Google App Engine
서버는 [Google App Engine][3](이하 GAE)를 사용합니다.
돈도 없고 무료로 이정도 퀄리티의 서버를 제공해주는 구글님께 감사할 뿐이죠. ^^

3. Google Clude Messaging for Android
안드로이드 단말기에 푸시 메시지를 발송하기 위해서 [GCM-Server][4]를 사용합니다.

License
-------

    Copyright 2013 Kwangmyung Choi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



 [1]: http://www.dvdprime.com
 [2]: https://jersey.java.net/
 [3]: https://developers.google.com/appengine/?hl=ko
 [4]: http://developer.android.com/google/gcm/server.html