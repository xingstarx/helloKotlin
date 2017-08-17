# helloKotlin
helloKotlin sample, kotlin android 小技巧汇总

1. 使用@JvmField注解保证属性变量为public而不是private

2. activity或者是fragment都可以使用kotlin的Android Extensions 直接生成对应的 View 作为属性的功能，
只需要引入`import kotlinx.android.synthetic.main.fragment_main.*`类似这样的包，有一点需要注意的是，
在fragment中，使用时需要确保viewId的使用不可以放在onCreateView中，
具体看这个https://stackoverflow.com/questions/34541650/kotlin-android-extensions-and-fragments