Class {
	#name : 'AoCDay03Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay03Test >> setUp [
	super setUp.
	self day: '3'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay03 new.
]

{ #category : 'tests' }
AoCDay03Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 7727.
]

{ #category : 'tests' }
AoCDay03Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 157.
]
