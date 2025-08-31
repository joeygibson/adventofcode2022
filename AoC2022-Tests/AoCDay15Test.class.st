Class {
	#name : 'AoCDay15Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'setup' }
AoCDay15Test >> setUp [
	super setUp.
	self day: '15'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay15 new.
]

{ #category : 'executing' }
AoCDay15Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	self increaseTimeout: 600.
	res := inst part1: lines rowOfInterest: 2000000.

	self assert: res equals: 5838453.
		
]

{ #category : 'executing' }
AoCDay15Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines rowOfInterest: 10.

	self assert: res equals: 26.
		
]

{ #category : 'executing' }
AoCDay15Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines maxCoord: 4000000.

	self assert: res equals: 12413999391794.
		
]

{ #category : 'executing' }
AoCDay15Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines maxCoord: 20.

	self assert: res equals: 56000011.
		
]
