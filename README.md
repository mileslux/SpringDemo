# SpringDemo

Изначально этот проект предназначался для реализации [тестового задания](https://github.com/newpointer/currency-rate-api). Впоследствии проект стал полем для исследования различных фич:

* Версионирование API
* Разнесение объекта от способа его представления
* Кэширование 

### Версионирование API

* Примеры того, как представлено версионирование API у ряда крупных компаний. Присутствие *api.* или *developer.* в названии хоста прямо говорит о его предназначении.

```
https://developer.github.com/v3/
https://api.twitter.com/1/
https://api.foursquare.com/v2/
https://api.tumblr.com/v2
```

* Следует предоставлять несколько равносильных представлений API. 

```
https://api.myproject.com/v1/rate
https://api.myproject.com/latest/rate
https://api.myproject.com/rate
```

Пользователи API получают возможность самостоятельно выбрать способ привязки с версиям API. При этом старые версии API могут продолжать функционировать. При несовместимости старого API с новым можно отправлять 410 Gone. При совместимости - можно отправлять 301 Moved Permanently или 302 Found.

* В текущем проекте версионирование производится посредством аннотации **@ApiVersion** приписываемой к методам в контроллере:

```
@RequestMapping(value = "/{code}", method = RequestMethod.GET)
@ApiVersion(versions = {"1"}, latest = true)
public ResponseEntity<?> getRate(@CodeConstraint @PathVariable("code") String code) {
```

Результатом будет то, что тройка URL из пункта выше будут расцениваться как эквивалентные.

### Разнесение объекта от способа его представления

* Тестовое задание требовало возвращать ответ на запрос в форме отличной от стандартного Json-овского представления. Поэтому в проекте было реализовано задание представления возвращаемого объекта через аннотацию **@JsonResponse** приписываемую к методам в контроллере:

```
@RequestMapping(value = "/{code}", method = RequestMethod.GET)
@JsonResponse(mixins = {
    @JsonMixin(target = Rate.class, mixin = RateMixin.class)
})
public ResponseEntity<?> getRate(@CodeConstraint @PathVariable("code") String code) {
```

Здесь возвращаемый объект типа *Rate* будет представлен в виде, заданном в *RateMixin*. Вид задается на уровне контроллера, а это значит, что при помощи аннотации можно легко указывать какой метод в каком представлении будет возвращать ответ.

* Идея *@JsonResponse* принадлежит пользователю [jackmatt2](https://github.com/jackmatt2/JsonResponse). В проекте автора идеи эта аннотация работала только для *ResponseBody*. В этом проекте её действие было распространено и на *ResponseEntity*.

### Кэширование

* Начало рабочего дня. Тысячи пользователей ломятся узнать текущий курс валют. Неприемлемо было бы все запросы переадресовывать напрямую на сбербанковский API. Требуется кэширование ответов от сбербанка. Достаточно того, чтобы обработчик первого запроса получил текущий курс от API сбербанка, пока остальные обработчики дожидаются этого ответа. 

* Этот способ кэширования называется [Read Through Caching](http://docs.oracle.com/cd/E24290_01/coh.371/e22837/cache_rtwtwbra.htm#COHDG5179), реализован при помощи [Guava LoadingCache](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/cache/LoadingCache.html). *LoadingCache* как и *ConcurrentHashMap* использует прием *lock stripping*, повышающий эффективность при работе одновремено с несколькими потоками.

# Quick Start

```
git clone https://github.com/mileslux/SpringDemo.git
cd StarStaffLibrary
./gradlew build
```
